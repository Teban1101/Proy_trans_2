# 🧪 Guía de Pruebas: Puntos 2-5

## Requisitos previos

- ✅ Proyecto compilado (`mvn clean compile`)
- ✅ Spring Boot ejecutándose (`mvn spring-boot:run`)
- ✅ Base de datos poblada con datos de prueba
- ✅ IDs de usuarios válidos en base de datos (ej: 200001)

---

## ✅ PUNTO 2: Transacciones en RF8

### Prueba 1: Éxito total
**Objetivo**: Verificar que los 3 pasos se registran atomicamente

```bash
curl -X POST "http://localhost:8080/servicios/solicitarAuto" \
  -d "id_usuario=200001" \
  -d "id_punto_geografico_origen=1" \
  -d "id_punto_geografico_destino=2" \
  -d "distancia_km=5.5" \
  -d "id_tipo_servicio=1"
```

**Resultado esperado**:
```json
{
  "status": "success",
  "id_servicio": 123,
  "mensaje": "Servicio solicitado exitosamente"
}
```

**Verificación en BD**:
```sql
-- Verificar que se registró SERVICIO, SERVICIO_DESTINO (origen), SERVICIO_DESTINO (destino)
SELECT * FROM SERVICIO WHERE ID_USUARIO_SERVICIO = 200001 ORDER BY ID DESC LIMIT 1;
SELECT * FROM SERVICIO_DESTINO WHERE ID_SERVICIO = 123;
```

---

### Prueba 2: Fallo - Sin tarjeta de crédito
**Objetivo**: Verificar rollback automático

```bash
# Primero, eliminar tarjeta del usuario 200001
DELETE FROM TARJETA_CREDITO WHERE ID_USUARIO = 200001;

# Intentar solicitar servicio
curl -X POST "http://localhost:8080/servicios/solicitarAuto" \
  -d "id_usuario=200001" \
  -d "id_punto_geografico_origen=1" \
  -d "id_punto_geografico_destino=2" \
  -d "distancia_km=5.5" \
  -d "id_tipo_servicio=1"
```

**Resultado esperado**:
```json
{
  "status": "error",
  "mensaje": "El usuario no tiene tarjeta de crédito registrada"
}
```

**Verificación en BD**:
```sql
-- NO debe haber nuevo SERVICIO registrado (ROLLBACK)
SELECT COUNT(*) FROM SERVICIO WHERE ID_USUARIO_SERVICIO = 200001;
-- Debe ser el mismo count que antes
```

---

### Prueba 3: Fallo - Sin conductores disponibles
**Objetivo**: Verificar rollback cuando no hay conductores

```bash
-- Primero, marcar todos los conductores como NO disponibles
UPDATE DISPONIBILIDAD SET DISPONIBLE = FALSE 
WHERE ID_CONDUCTOR IN (SELECT ID_CONDUCTOR FROM USUARIO_CONDUCTOR);

# Intentar solicitar servicio
curl -X POST "http://localhost:8080/servicios/solicitarAuto" \
  -d "id_usuario=200001" \
  -d "id_punto_geografico_origen=1" \
  -d "id_punto_geografico_destino=2" \
  -d "distancia_km=5.5" \
  -d "id_tipo_servicio=1"
```

**Resultado esperado**:
```json
{
  "status": "error",
  "mensaje": "No hay conductores disponibles en este momento"
}
```

**Verificación en BD**:
```sql
-- NO debe haber nuevo SERVICIO registrado (ROLLBACK)
SELECT COUNT(*) FROM SERVICIO WHERE HORA_INICIO > SYSDATE - 1/24;
```

---

## ✅ PUNTO 3: RFC1 con Niveles de Aislamiento

### Prueba 1: READ_COMMITTED
**Objetivo**: Verificar que la consulta obtiene datos sin bloqueos excesivos

```bash
curl -X GET "http://localhost:8080/rfc1/historial-usuario-committed/200001"
```

**Resultado esperado**:
```json
{
  "usuario_id": 200001,
  "aislamiento": "READ_COMMITTED",
  "timeout_segundos": 30,
  "servicios": [
    {
      "id_servicio": 123,
      "hora_inicio": "2025-01-15 10:30:00",
      "hora_fin": "2025-01-15 10:45:00",
      "distancia": 5.5,
      "costo_total": 25000
    }
  ],
  "total_servicios": 42,
  "tiempo_consulta_ms": 150
}
```

**Características verificadas**:
- ✅ Retorna datos rápidamente (< 200ms típico)
- ✅ No bloquea escrituras concurrentes
- ✅ Modo: READ_COMMITTED

---

### Prueba 2: SERIALIZABLE
**Objetivo**: Verificar máxima consistencia a costa de rendimiento

```bash
curl -X GET "http://localhost:8080/rfc1/historial-usuario-serializable/200001"
```

**Resultado esperado**:
```json
{
  "usuario_id": 200001,
  "aislamiento": "SERIALIZABLE",
  "timeout_segundos": 30,
  "servicios": [...],
  "total_servicios": 42,
  "tiempo_consulta_ms": 450
}
```

**Características verificadas**:
- ✅ Mismo resultado que READ_COMMITTED
- ✅ Tiempo más lento (> 400ms típico)
- ✅ Modo: SERIALIZABLE con máxima consistencia

---

### Prueba 3: Resumen de usuario (READ_COMMITTED)
**Objetivo**: Obtener totales agregados

```bash
curl -X GET "http://localhost:8080/rfc1/resumen-usuario/200001"
```

**Resultado esperado**:
```json
{
  "usuario_id": 200001,
  "aislamiento": "READ_COMMITTED",
  "total_servicios": 42,
  "costo_total": 1050000,
  "distancia_total_km": 285.5,
  "servicios_completados": 38,
  "servicios_en_progreso": 2,
  "servicios_cancelados": 2
}
```

---

### Prueba 4: Servicios completados (SERIALIZABLE)
**Objetivo**: Obtener solo servicios finalizados

```bash
curl -X GET "http://localhost:8080/rfc1/servicios-completados/200001"
```

**Resultado esperado**:
```json
{
  "usuario_id": 200001,
  "aislamiento": "SERIALIZABLE",
  "servicios_completados": 38,
  "servicios_totales": 42,
  "porcentaje_completados": 90.48,
  "listado": [
    {
      "id_servicio": 123,
      "fecha": "2025-01-15",
      "hora_inicio": "10:30:00",
      "hora_fin": "10:45:00",
      "duracion_minutos": 15,
      "distancia_km": 5.5,
      "calificacion": 4.5
    }
  ]
}
```

---

## ✅ PUNTO 4: Concurrencia - SERIALIZABLE

### Prueba: RFC1 bloquea a RF8
**Objetivo**: Demostrar que SERIALIZABLE causa bloqueos

```bash
# RFC1 comienza primero (SELECT con bloqueos)
# RF8 intenta comenzar 1 segundo después
# RF8 debe ESPERAR a que RFC1 termine

curl -X POST "http://localhost:8080/pruebas-concurrencia/test-serializable" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "id_usuario=200001&id_servicio_rf8=999&distancia_km=10.5&id_tipo_servicio=1&id_usuario_servicio=200001&id_punto_origen=1&id_punto_destino=2"
```

**Resultado esperado**:
```json
{
  "prueba": "CONCURRENCIA SERIALIZABLE (Punto 4)",
  "usuario_id": 200001,
  "tiempo_total_ms": 3500,
  "resultado_esperado": "RFC1 debe esperar a que RF8 termine o RF8 debe esperar a RFC1",
  "eventos": [
    {
      "timestamp_ms": 0,
      "thread": 1,
      "operacion": "RFC1-SERIALIZABLE",
      "evento": "Iniciando consulta..."
    },
    {
      "timestamp_ms": 500,
      "thread": 1,
      "operacion": "RFC1-SERIALIZABLE",
      "evento": "Consulta completada (5 servicios)"
    },
    {
      "timestamp_ms": 1000,
      "thread": 2,
      "operacion": "RF8",
      "evento": "Iniciando solicitud de servicio..."
    },
    {
      "timestamp_ms": 1800,
      "thread": 2,
      "operacion": "RF8",
      "evento": "Servicio solicitado"
    }
  ],
  "observacion": "En SERIALIZABLE, ambas operaciones son serializadas (ejecutadas secuencialmente)"
}
```

**Validación**:
- ✅ RFC1 completa primero (500ms)
- ✅ RF8 comienza a los 1000ms (espera disponibilidad)
- ✅ Tiempo total > tiempo RFC1 + tiempo RF8 (por el bloqueo)
- ✅ Eventos muestran ejecución secuencial

---

## ✅ PUNTO 5: Concurrencia - READ_COMMITTED

### Prueba: RFC1 y RF8 se ejecutan concurrentemente
**Objetivo**: Demostrar menos bloqueos en READ_COMMITTED

```bash
# RFC1 comienza primero (SELECT sin bloqueos exclusivos)
# RF8 intenta comenzar 500ms después
# RF8 PUEDE proceder sin esperar a RFC1

curl -X POST "http://localhost:8080/pruebas-concurrencia/test-read-committed" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "id_usuario=200001&id_servicio_rf8=998&distancia_km=15.3&id_tipo_servicio=1&id_usuario_servicio=200001&id_punto_origen=1&id_punto_destino=2"
```

**Resultado esperado**:
```json
{
  "prueba": "CONCURRENCIA READ_COMMITTED (Punto 5)",
  "usuario_id": 200001,
  "tiempo_total_ms": 2500,
  "resultado_esperado": "RFC1 y RF8 ejecutan concurrentemente",
  "eventos": [
    {
      "timestamp_ms": 0,
      "thread": 1,
      "operacion": "RFC1-READ_COMMITTED",
      "evento": "Iniciando consulta..."
    },
    {
      "timestamp_ms": 500,
      "thread": 2,
      "operacion": "RF8",
      "evento": "Iniciando solicitud de servicio..."
    },
    {
      "timestamp_ms": 800,
      "thread": 2,
      "operacion": "RF8",
      "evento": "Servicio solicitado"
    },
    {
      "timestamp_ms": 2000,
      "thread": 1,
      "operacion": "RFC1-READ_COMMITTED",
      "evento": "Consulta completada (5 servicios)"
    }
  ],
  "observacion": "En READ_COMMITTED, RFC1 y RF8 se ejecutan casi simultáneamente sin bloqueos excesivos"
}
```

**Validación**:
- ✅ RF8 comienza a los 500ms (antes de que RFC1 termine)
- ✅ RF8 completa a los 800ms (sin esperar a RFC1)
- ✅ RFC1 completa a los 2000ms
- ✅ Tiempo total = max(2000ms, 800ms) = 2000ms (más rápido que SERIALIZABLE)
- ✅ Eventos muestran ejecución concurrente

---

## 📊 Comparación de Resultados

### SERIALIZABLE (Punto 4)
```
Línea de tiempo:
0ms     500ms   1000ms  1800ms
|----RFC1----|   |---RF8---|
       ↑                ↑
    Bloqueo    Espera RFC1
Tiempo total: 1800ms
```

### READ_COMMITTED (Punto 5)
```
Línea de tiempo:
0ms  500ms  800ms    2000ms
|RFC1 en progreso--|
      |--RF8--| (sin esperar)
Tiempo total: 2000ms
```

---

## 🔍 Verificaciones en Base de Datos

### Después de todas las pruebas

```sql
-- Verificar servicios creados en pruebas
SELECT ID_SERVICIO, ID_USUARIO_SERVICIO, ID_CONDUCTOR, 
       HORA_INICIO, HORA_FIN FROM SERVICIO 
WHERE ID_USUARIO_SERVICIO = 200001 
ORDER BY HORA_INICIO DESC;

-- Verificar que los destinos se crearon correctamente
SELECT ID_SERVICIO, ID_PUNTO_GEOGRAFICO, ID_USUARIO_ORIGEN 
FROM SERVICIO_DESTINO 
WHERE ID_SERVICIO IN (SELECT ID_SERVICIO FROM SERVICIO 
                     WHERE ID_USUARIO_SERVICIO = 200001)
ORDER BY ID_SERVICIO DESC;

-- Verificar contador de revisiones (si las hubiera)
SELECT COUNT(*) FROM REVISION 
WHERE ID_USUARIO_REVISOR = 200001 OR ID_USUARIO_REVISADO = 200001;
```

---

## 📝 Reporte de Pruebas

### Usar este template para documentar resultados:

```
═══════════════════════════════════════════════════════
REPORTE DE PRUEBAS - PUNTOS 2-5
═══════════════════════════════════════════════════════

PUNTO 2: @Transactional en RF8
├─ Prueba 1 (Éxito): [✅/❌] Resultado: ________
├─ Prueba 2 (Rollback sin tarjeta): [✅/❌] Resultado: ________
└─ Prueba 3 (Rollback sin conductores): [✅/❌] Resultado: ________

PUNTO 3: RFC1 con Niveles de Aislamiento
├─ Prueba 1 (READ_COMMITTED): [✅/❌] Tiempo: ____ms
├─ Prueba 2 (SERIALIZABLE): [✅/❌] Tiempo: ____ms
├─ Prueba 3 (Resumen): [✅/❌] Total servicios: ____
└─ Prueba 4 (Completados): [✅/❌] Completados: ____

PUNTO 4: Concurrencia SERIALIZABLE
└─ Resultado: [✅/❌] Tiempo total: ____ms
   Observación: RFC1 [esperó/no esperó] a RF8

PUNTO 5: Concurrencia READ_COMMITTED
└─ Resultado: [✅/❌] Tiempo total: ____ms
   Observación: RFC1 y RF8 [se ejecutaron concurrentemente/fueron secuenciales]

═══════════════════════════════════════════════════════
RESULTADO FINAL: [✅ TODOS PASADOS / ❌ FALLOS DETECTADOS]
═══════════════════════════════════════════════════════
```

---

## 🆘 Troubleshooting

### Error: "No se encuentra el endpoint"
- Verificar que el servidor está ejecutándose en `http://localhost:8080`
- Verificar que los archivos Java están compilados

### Error: "Usuario no encontrado"
- Verificar que existe usuario con ID 200001 en BD
- Usar `SELECT ID_USUARIO FROM USUARIO WHERE ID_USUARIO = 200001;`

### Error de timeout (30 segundos)
- RFC1 tardó más de 30 segundos
- Verificar carga de BD
- Aumentar timeout en anotación @Transactional

### Las transacciones no se revierten
- Verificar que `@Transactional(rollbackFor = Exception.class)` está presente
- Verificar que se está lanzando excepción (no retornando error string)

---

## ✅ Checklist Final

- [ ] Punto 2: Todas las 3 pruebas de transacción pasaron
- [ ] Punto 3: Los 4 endpoints RFC1 retornan datos correctos
- [ ] Punto 3: Diferencia visible entre READ_COMMITTED y SERIALIZABLE
- [ ] Punto 4: RFC1 se bloquea en SERIALIZABLE
- [ ] Punto 5: RFC1 y RF8 se ejecutan concurrentemente en READ_COMMITTED
- [ ] Base de datos: Todos los registros se persistieron correctamente
- [ ] Documentación: Reporte de pruebas completado

---

**Nota**: Ejecutar las pruebas en orden (Punto 2 → 3 → 4 → 5) para mejor validación.
