# Documentación: Puntos 2-5 del Proyecto

## Resumen de Implementación

Se han implementado exitosamente los cuatro puntos requeridos del proyecto:

---

## **PUNTO 2 (20%) - Implementación del RF8: Transacciones en Java Spring**

### Ubicación del código:
- **Archivo**: `src/main/java/uniandes/edu/co/proyecto/controller/ServicioController.java`
- **Método**: `solicitarServicioAuto()` (línea ~181)

### Cambios implementados:

1. **Importación de @Transactional**:
   ```java
   import org.springframework.transaction.annotation.Transactional;
   ```

2. **Anotación en el método RF8**:
   ```java
   @PostMapping("/solicitarAuto")
   @Transactional(rollbackFor = Exception.class)
   public String solicitarServicioAuto(...) {
   ```

### Características de la implementación:

✅ **Atomicidad**: Todas las operaciones se ejecutan dentro de una transacción:
   - OPERACION 1: Registrar inicio del viaje y asignar conductor
   - OPERACION 2: Registrar punto de origen
   - OPERACION 3: Registrar punto de destino

✅ **Rollback automático**: Si cualquier operación falla, toda la transacción se revierte

✅ **Manejo de excepciones**: Las excepciones son lanzadas para provocar rollback

✅ **Validaciones previas**: Se verifican todas las precondiciones antes de iniciar operaciones

### Funcionamiento:
- Si el usuario NO tiene tarjeta de crédito → Se lanza excepción → Transacción se revierte
- Si no hay conductores disponibles → Se lanza excepción → Transacción se revierte
- Si falla la inserción del servicio → Se lanza excepción → Puntos geográficos NO se registran
- Si todos los pasos son exitosos → Todo se commitea correctamente

---

## **PUNTO 3 (20%) - Implementación de RFC1 con Diferentes Niveles de Aislamiento**

### Ubicación del código:
- **Archivo**: `src/main/java/uniandes/edu/co/proyecto/controller/HistorialServiciosController.java` (NUEVO)

### Endpoints RFC1 implementados:

#### 1. **READ_COMMITTED (Aislamiento por defecto)**
```
GET /rfc1/historial-usuario-committed/{id_usuario}
```
- **Aislamiento**: `Isolation.READ_COMMITTED`
- **Timeout**: 30 segundos
- **Características**: 
  - Permite non-repeatable reads
  - Permite phantom reads
  - NO permite dirty reads
  - Rendimiento mejor (menos bloqueos)

#### 2. **SERIALIZABLE (Aislamiento máximo)**
```
GET /rfc1/historial-usuario-serializable/{id_usuario}
```
- **Aislamiento**: `Isolation.SERIALIZABLE`
- **Timeout**: 30 segundos
- **Características**:
  - Previene dirty reads, non-repeatable reads y phantom reads
  - Aislamiento máximo
  - Rendimiento menor (más bloqueos)
  - Consistencia garantizada

#### 3. **Resumen de usuario** (READ_COMMITTED)
```
GET /rfc1/resumen-usuario/{id_usuario}
```
- Retorna: total de servicios, costo total, distancia total

#### 4. **Servicios completados** (SERIALIZABLE)
```
GET /rfc1/servicios-completados/{id_usuario}
```
- Retorna: servicios completados vs total

### Ejemplo de uso:
```bash
# READ_COMMITTED
curl http://localhost:8080/rfc1/historial-usuario-committed/200001

# SERIALIZABLE
curl http://localhost:8080/rfc1/historial-usuario-serializable/200001
```

---

## **PUNTO 4 (20%) - Escenario de Concurrencia: SERIALIZABLE**

### Ubicación del código:
- **Archivo**: `src/main/java/uniandes/edu/co/proyecto/controller/ConcurrenciaTestController.java` (NUEVO)
- **Endpoint**: `POST /pruebas-concurrencia/test-serializable`

### Descripción del escenario:

**Secuencia temporal**:
```
Tiempo 0ms:    [RFC1-SERIALIZABLE] Inicia consulta (SELECT)
               ↓
Tiempo 1000ms: [RF8] Inicia solicitud de servicio (INSERT/UPDATE)
               ↓
Resultado:     RFC1 debe ESPERAR a que RF8 complete
               (bloqueo por aislamiento SERIALIZABLE)
```

### Prueba del escenario:

```bash
curl -X POST "http://localhost:8080/pruebas-concurrencia/test-serializable" \
  -d "id_usuario=200001" \
  -d "id_servicio_rf8=999" \
  -d "distancia_km=10.5" \
  -d "id_tipo_servicio=1" \
  -d "id_usuario_servicio=200001" \
  -d "id_punto_origen=1" \
  -d "id_punto_destino=2"
```

### Resultado esperado:

```json
{
  "prueba": "CONCURRENCIA SERIALIZABLE (Punto 4)",
  "usuario_id": 200001,
  "tiempo_total_ms": 3500,
  "resultado_esperado": "RFC1 debe esperar a que RF8 termine",
  "eventos": [
    "0ms - [1] RFC1-SERIALIZABLE: Iniciando consulta...",
    "500ms - [1] RFC1-SERIALIZABLE: Consulta completada (5 servicios)",
    "1000ms - [2] RF8: Iniciando solicitud de servicio...",
    "1800ms - [2] RF8: Servicio solicitado"
  ],
  "observacion": "En SERIALIZABLE, RF8 espera a que RFC1 complete"
}
```

**Validación**: RFC1 debe completarse ANTES de que RF8 termine (bloqueo de lectura/escritura)

---

## **PUNTO 5 (20%) - Escenario de Concurrencia: READ_COMMITTED**

### Ubicación del código:
- **Archivo**: `src/main/java/uniandes/edu/co/proyecto/controller/ConcurrenciaTestController.java` (NUEVO)
- **Endpoint**: `POST /pruebas-concurrencia/test-read-committed`

### Descripción del escenario:

**Secuencia temporal**:
```
Tiempo 0ms:    [RFC1-READ_COMMITTED] Inicia consulta (SELECT)
               ↓
Tiempo 500ms:  [RF8] Inicia solicitud de servicio (INSERT/UPDATE)
               ↓
Resultado:     RFC1 y RF8 pueden ejecutar CONCURRENTEMENTE
               (menos bloqueos que SERIALIZABLE)
```

### Prueba del escenario:

```bash
curl -X POST "http://localhost:8080/pruebas-concurrencia/test-read-committed" \
  -d "id_usuario=200001" \
  -d "id_servicio_rf8=998" \
  -d "distancia_km=15.3" \
  -d "id_tipo_servicio=1" \
  -d "id_usuario_servicio=200001" \
  -d "id_punto_origen=1" \
  -d "id_punto_destino=2"
```

### Resultado esperado:

```json
{
  "prueba": "CONCURRENCIA READ_COMMITTED (Punto 5)",
  "usuario_id": 200001,
  "tiempo_total_ms": 2500,
  "resultado_esperado": "RFC1 y RF8 ejecutan concurrentemente",
  "eventos": [
    "0ms - [1] RFC1-READ_COMMITTED: Iniciando consulta...",
    "500ms - [2] RF8: Iniciando solicitud de servicio...",
    "800ms - [2] RF8: Servicio solicitado",
    "2000ms - [1] RFC1-READ_COMMITTED: Consulta completada (5 servicios)"
  ],
  "observacion": "RFC1 y RF8 se ejecutan casi simultáneamente (menos bloqueos)"
}
```

**Validación**: RF8 completa ANTES de que RFC1 termine (ejecución concurrente)

---

## **Comparación SERIALIZABLE vs READ_COMMITTED**

| Aspecto | SERIALIZABLE | READ_COMMITTED |
|---------|--------------|----------------|
| **Aislamiento** | Máximo | Estándar |
| **Bloqueos** | Muchos (conservador) | Pocos (optimista) |
| **Rendimiento** | Lento | Rápido |
| **Concurrencia** | Baja | Alta |
| **Dirty Reads** | ✗ No | ✗ No |
| **Non-repeatable Reads** | ✗ No | ✓ Sí |
| **Phantom Reads** | ✗ No | ✓ Sí |
| **Deadlocks** | Posible | Menos probable |

---

## **Resumen de archivos modificados/creados**

### Modificados:
1. `ServicioController.java` - Agregado @Transactional a RF8
2. `ServicioRepository.java` - Agregado método `findByIdUsuarioServicio()`

### Creados:
1. `HistorialServiciosController.java` - RFC1 con niveles de aislamiento
2. `ConcurrenciaTestController.java` - Pruebas de concurrencia

---

## **Cómo ejecutar las pruebas**

### Compilar:
```bash
cd plantilla-proyecto-main
mvn clean compile
mvn spring-boot:run
```

### Probar RFC1:
```bash
# READ_COMMITTED
curl http://localhost:8080/rfc1/historial-usuario-committed/200001

# SERIALIZABLE
curl http://localhost:8080/rfc1/historial-usuario-serializable/200001

# Resumen
curl http://localhost:8080/rfc1/resumen-usuario/200001

# Servicios completados
curl http://localhost:8080/rfc1/servicios-completados/200001
```

### Probar Concurrencia:
```bash
# SERIALIZABLE - RF8 espera
curl -X POST "http://localhost:8080/pruebas-concurrencia/test-serializable" \
  -d "id_usuario=200001&id_servicio_rf8=999&distancia_km=10.5&id_tipo_servicio=1&id_usuario_servicio=200001&id_punto_origen=1&id_punto_destino=2"

# READ_COMMITTED - Ejecutan concurrentemente
curl -X POST "http://localhost:8080/pruebas-concurrencia/test-read-committed" \
  -d "id_usuario=200001&id_servicio_rf8=998&distancia_km=15.3&id_tipo_servicio=1&id_usuario_servicio=200001&id_punto_origen=1&id_punto_destino=2"
```

---

## **Validaciones completadas**

✅ Punto 2: @Transactional en RF8 implementado
✅ Punto 3: RFC1 con READ_COMMITTED y SERIALIZABLE implementado
✅ Punto 4: Prueba de concurrencia SERIALIZABLE implementada
✅ Punto 5: Prueba de concurrencia READ_COMMITTED implementada
✅ Timeout de 30 segundos en todas las transacciones
✅ Manejo de excepciones y rollback automático
✅ Logging de eventos de concurrencia para observación

---

**Nota final**: Todos los cambios mantienen la compatibilidad con los RFs anteriores (RF1-RF11).
