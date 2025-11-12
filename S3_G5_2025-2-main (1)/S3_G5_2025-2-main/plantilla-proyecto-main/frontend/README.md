# 🚗 AlpesCab - Frontend de Pruebas

Frontend completo en HTML/CSS/JavaScript para probar todos los requerimientos funcionales (RF) y requerimientos funcionales de consulta (RFC) del sistema AlpesCab.

## 📋 Estructura del Frontend

```
frontend/
├── index.html                          # Página principal con navegación
├── css/
│   └── styles.css                      # Estilos CSS principales
├── js/
│   └── common.js                       # Funciones JavaScript comunes
├── rf1-ciudades.html                   # RF1 - Gestión de Ciudades
├── rf2-usuarios-servicio.html          # RF2 - Usuarios de Servicios
├── rf3-usuarios-conductor.html         # RF3 - Usuarios Conductores
├── rf4-vehiculos.html                  # RF4 - Gestión de Vehículos
├── rf5-rf6-disponibilidad.html         # RF5/RF6 - Disponibilidad
├── rf7-puntos-geograficos.html         # RF7 - Puntos Geográficos
├── rf8-solicitar-servicio.html         # RF8 - Solicitar Servicio
├── rf9-finalizar-servicio.html         # RF9 - Finalizar Servicio
├── rf10-rf11-revisiones.html           # RF10/RF11 - Revisiones
├── rfc1-historial-servicios.html       # RFC1 - Historial de Servicios
├── rfc2-top-conductores.html           # RFC2 - Top Conductores
├── rfc3-dinero-conductores.html        # RFC3 - Dinero por Conductores
├── rfc4-uso-por-ciudad.html            # RFC4 - Uso por Ciudad
├── admin-tipos.html                    # Administración de Tipos
├── admin-tarifas.html                  # Gestión de Tarifas
├── admin-tarjetas.html                 # Tarjetas de Crédito
└── test-completo.html                  # Test Automatizado Completo
```

## 🚀 Instrucciones de Uso

### 1. Prerequisitos
- **Backend ejecutándose**: El servidor Spring Boot debe estar corriendo en `http://localhost:8080`
- **Base de datos configurada**: Oracle DB debe estar configurada y conectada
- **Navegador web moderno**: Chrome, Firefox, Edge o Safari

### 2. Ejecución
1. Abrir `index.html` en un navegador web
2. Verificar que el backend esté corriendo
3. Navegar por las diferentes secciones según sea necesario

### 3. Orden Recomendado de Pruebas

#### Paso 1: Configuración Básica
1. **RF1 - Ciudades**: Registrar ciudades donde se prestarán servicios
2. **Tipos de Servicios**: Crear tipos de servicios (Transporte de pasajeros, etc.)
3. **Tipos de Vehículos**: Crear tipos de vehículos (Automóvil, SUV, etc.)
4. **Niveles de Transporte**: Crear niveles (Estándar, Premium, etc.)
5. **Tarifas**: Configurar tarifas por tipo de servicio y nivel

#### Paso 2: Usuarios
6. **RF2 - Usuarios de Servicios**: Registrar usuarios que utilizarán el servicio
7. **RF3 - Usuarios Conductores**: Registrar conductores
8. **Tarjetas de Crédito**: Registrar medios de pago para usuarios

#### Paso 3: Vehículos y Disponibilidad
9. **RF4 - Vehículos**: Registrar vehículos para conductores
10. **RF7 - Puntos Geográficos**: Registrar ubicaciones
11. **RF5 - Disponibilidad**: Configurar horarios de conductores

#### Paso 4: Servicios
12. **RF8 - Solicitar Servicio**: Probar solicitud de servicios
13. **RF9 - Finalizar Servicio**: Completar servicios
14. **RF10/RF11 - Revisiones**: Dejar calificaciones

#### Paso 5: Consultas
15. **RFC1 - RFC4**: Probar todas las consultas

## 🧪 Test Automatizado

La página `test-completo.html` ejecuta automáticamente todos los requerimientos en secuencia:

### Características del Test:
- ✅ **Datos de prueba predefinidos**
- ✅ **Ejecución secuencial automática**
- ✅ **Validación de dependencias**
- ✅ **Reporte de resultados en tiempo real**
- ✅ **Función de limpieza de datos**

### Para usar el test automático:
1. Abrir `test-completo.html`
2. Hacer clic en "🚀 Ejecutar Test Completo"
3. Observar los resultados en tiempo real
4. Usar "🗑️ Resetear Datos" para limpiar después

## 📊 Requerimientos Implementados

### Requerimientos Funcionales (RF)
- ✅ **RF1**: Registrar una ciudad
- ✅ **RF2**: Registrar un usuario de servicios
- ✅ **RF3**: Registrar un usuario conductor
- ✅ **RF4**: Registrar un vehículo para un usuario conductor
- ✅ **RF5**: Registrar la disponibilidad de un usuario conductor y su vehículo
- ✅ **RF6**: Modificar la disponibilidad de un vehículo para servicios
- ✅ **RF7**: Registrar un punto geográfico
- ✅ **RF8**: Solicitar un servicio por parte de un usuario de servicios
- ✅ **RF9**: Registrar el final de un viaje
- ✅ **RF10**: Dejar una revisión por parte del usuario de servicios
- ✅ **RF11**: Dejar una revisión por parte de un usuario conductor

### Requerimientos Funcionales de Consulta (RFC)
- ✅ **RFC1**: Consultar el historial de todos los servicios pedidos por un usuario
- ✅ **RFC2**: Mostrar los 20 usuarios conductores que más servicios han prestado
- ✅ **RFC3**: Mostrar el total de dinero obtenido por usuarios conductores para cada vehículo
- ✅ **RFC4**: Mostrar la utilización de servicios en una ciudad durante un rango de fechas

## 🎯 Características del Frontend

### Interfaz de Usuario
- **Diseño Responsivo**: Funciona en desktop y móvil
- **Navegación Intuitiva**: Menú principal con acceso directo a cada RF/RFC
- **Validación en Tiempo Real**: Campos requeridos validados automáticamente
- **Feedback Visual**: Mensajes de éxito/error inmediatos

### Funcionalidades Avanzadas
- **Tablas Dinámicas**: Visualización de datos en formato tabla
- **Formato de Datos**: Fechas y monedas formateadas correctamente
- **Carga Automática**: Selects poblados automáticamente desde la API
- **Gestión de Estados**: Indicadores de éxito/fallo para cada operación

### Integración con Backend
- **API REST Completa**: Todas las operaciones CRUD implementadas
- **Manejo de Errores**: Captura y muestra errores del backend
- **Validaciones**: Verificaciones de dependencias y consistencia
- **Transacciones**: Operaciones complejas como RF8 totalmente implementadas

## 🔧 Configuración

### Variables de Configuración (en js/common.js)
```javascript
const API_BASE_URL = 'http://localhost:8080';  // URL del backend
```

### Dependencias
- No requiere librerías externas
- Usa APIs nativas del navegador (Fetch, DOM, etc.)
- Compatible con navegadores modernos

## 📝 Notas Importantes

### RF8 - Solicitar Servicio
Este requerimiento implementa la lógica completa:
1. ✅ Verificación de medio de pago registrado
2. ✅ Búsqueda de conductor disponible
3. ✅ Asignación automática de conductor
4. ✅ Cálculo de tarifa basado en distancia y nivel
5. ✅ Registro del inicio del viaje

### Validaciones Implementadas
- **RF5/RF6**: Validación de superposición de disponibilidades
- **RF8**: Verificación de medios de pago antes de solicitar servicio
- **Todas las operaciones**: Validación de campos requeridos
- **Dependencias**: Verificación de existencia de registros relacionados

### Datos de Prueba Sugeridos
El sistema incluye datos de prueba preconfigurados que se pueden usar para validar todas las funcionalidades sin necesidad de crear datos manualmente.

## 🆘 Resolución de Problemas

### Backend no responde
- Verificar que el servidor Spring Boot esté ejecutándose en puerto 8080
- Revisar la consola del navegador para errores de CORS
- Confirmar que la base de datos esté conectada

### Errores de validación
- Verificar que todos los campos requeridos estén completos
- Revisar que las dependencias estén creadas (ciudades, tipos, etc.)
- Confirmar que los IDs referenciados existan

### Datos no se cargan
- Verificar conexión a internet
- Revisar la consola del navegador para errores JavaScript
- Confirmar que los endpoints del backend estén respondiendo

## 📞 Soporte

Este frontend está diseñado para ser completamente funcional con el backend implementado. Todas las funcionalidades han sido probadas y validadas para cumplir con los requerimientos especificados.

---
**🎉 ¡Disfruta probando el sistema AlpesCab!**