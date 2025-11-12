# Implementación de Requerimientos Funcionales (RF) y Requerimientos Funcionales de Consulta (RFC)

## Requerimientos Funcionales (RF)

### ✅ RF1 - Registrar una ciudad
- **Endpoint**: `POST /ciudades/insertar`
- **Parámetros**: id_ciudad, nombre
- **Implementado en**: `CiudadController.insertarCiudad()`

### ✅ RF2 - Registrar un usuario de servicios
- **Endpoint**: `POST /usuariosServicio/registrar`
- **Parámetros**: cedula, nombre, correo (opcional), celular
- **Funcionalidad**: Registra el usuario base y lo asocia como usuario de servicios
- **Implementado en**: `UsuarioServicioController.registrarUsuarioServicio()`

### ✅ RF3 - Registrar un usuario conductor
- **Endpoint**: `POST /usuariosConductor/registrar`
- **Parámetros**: cedula, nombre, correo (opcional), celular
- **Funcionalidad**: Registra el usuario base y lo asocia como usuario conductor
- **Implementado en**: `UsuarioConductorController.registrarUsuarioConductor()`

### ✅ RF4 - Registrar un vehículo para un usuario conductor
- **Endpoint**: `POST /vehiculos/insertar`
- **Parámetros**: placa, marca, modelo, color, capacidad, id_usuario, id_tipo_vehiculo, id_nivel, id_ciudad
- **Implementado en**: `VehiculoController.insertarVehiculo()`

### ✅ RF5 - Registrar la disponibilidad de un usuario conductor y su vehículo
- **Endpoint**: `POST /disponibilidades/insertar`
- **Parámetros**: id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo
- **Validación**: Verifica que no se superponga con otras disponibilidades del mismo conductor
- **Implementado en**: `DisponibilidadController.insertarDisponibilidad()`

### ✅ RF6 - Modificar la disponibilidad de un vehículo para servicios
- **Endpoint**: `PUT /disponibilidades/actualizar`
- **Parámetros**: id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo
- **Validación**: Verifica que no se superponga con otras disponibilidades del mismo conductor
- **Implementado en**: `DisponibilidadController.actualizarDisponibilidad()`

### ✅ RF7 - Registrar un punto geográfico
- **Endpoint**: `POST /puntosGeograficos/insertar`
- **Parámetros**: id_punto, nombre, direccion, latitud, longitud, id_ciudad
- **Actualización**: Se agregó el campo id_ciudad al modelo PuntoGeografico
- **Implementado en**: `PuntoGeograficoController.insertarPunto()`

### ✅ RF8 - Solicitar un servicio por parte de un usuario de servicios
- **Endpoint**: `POST /servicios/solicitarAuto`
- **Parámetros**: id_servicio, distancia_km, id_tipo_servicio, id_usuario_servicio, id_punto_origen, id_punto_destino
- **Funcionalidades implementadas**:
  - ✅ Verificación de medio de pago registrado disponible
  - ✅ Búsqueda de conductor disponible cercano
  - ✅ Actualización del estado del conductor
  - ✅ Registro del inicio del viaje
  - ✅ Cálculo automático de la tarifa
- **Implementado en**: `ServicioController.solicitarServicioAuto()`

### ✅ RF9 - Registrar el final de un viaje
- **Endpoint**: `PUT /servicios/terminar/{id_servicio}`
- **Funcionalidad**: Actualiza el servicio marcando la hora de finalización
- **Implementado en**: `ServicioController.terminarServicio()`

### ✅ RF10 - Dejar una revisión por parte del usuario de servicios
- **Endpoint**: `POST /revisiones/usuario-servicio`
- **Parámetros**: id_revision, calificacion, comentario (opcional), id_servicio, id_usuario
- **Funcionalidad**: Registra una revisión con rol "PASAJERO"
- **Implementado en**: `RevisionController.dejarRevisionUsuarioServicio()`

### ✅ RF11 - Dejar una revisión por parte de un usuario conductor
- **Endpoint**: `POST /revisiones/usuario-conductor`
- **Parámetros**: id_revision, calificacion, comentario (opcional), id_servicio, id_usuario
- **Funcionalidad**: Registra una revisión con rol "CONDUCTOR"
- **Implementado en**: `RevisionController.dejarRevisionUsuarioConductor()`

## Requerimientos Funcionales de Consulta (RFC)

### ✅ RFC1 - Consultar el histórico de todos los servicios pedidos por un usuario
- **Endpoint**: `GET /servicios/rfc1?usuario={id_usuario}`
- **Funcionalidad**: Lista detallada de todos los servicios con información completa
- **Implementado en**: `ServicioController.rfc1HistoricoPorUsuario()`

### ✅ RFC2 - Mostrar los 20 usuarios conductores que más servicios han prestado
- **Endpoint**: `GET /servicios/rfc2`
- **Funcionalidad**: Ranking de conductores por número de servicios prestados
- **Implementado en**: `ServicioController.rfc2Top20Conductores()`

### ✅ RFC3 - Mostrar el total de dinero obtenido por usuarios conductores para cada vehículo
- **Endpoint detallado**: `GET /servicios/rfc3?comisionPct={porcentaje}`
- **Endpoint agregado**: `GET /servicios/rfc3/agregado?comisionPct={porcentaje}`
- **Funcionalidad**: Calcula el dinero neto obtenido después de descontar comisiones
- **Implementado en**: 
  - `ServicioController.rfc3DetallePorServicio()`
  - `ServicioController.rfc3AgregadoPorVehiculo()`

### ✅ RFC4 - Mostrar la utilización de servicios en una ciudad durante un rango de fechas
- **Endpoint**: `GET /servicios/rfc4?ciudad={nombre}&ini={fecha_inicio}&fin={fecha_fin}`
- **Funcionalidad**: Estadísticas de uso por tipo de servicio y nivel de transporte con porcentajes
- **Implementado en**: `ServicioController.rfc4UsoPorCiudadYRango()`

## Validaciones y Características Especiales

- **RF5/RF6**: Validación de superposición de disponibilidades
- **RF8**: Verificación completa de medios de pago y asignación automática de conductores
- **RF8**: Cálculo automático de tarifas basado en distancia y nivel de transporte
- **RF7**: Modelo actualizado para incluir ciudad en puntos geográficos
- **RFC3**: Cálculo de ganancias netas considerando comisiones de la empresa
- **RFC4**: Análisis estadístico con porcentajes de utilización

## Endpoints Adicionales de CRUD

Todos los modelos incluyen operaciones CRUD completas:
- Insertar, actualizar, eliminar y consultar individual
- Listar todos los registros
- Operaciones transaccionales seguras

## Consideraciones Técnicas

- Uso de `@Transactional` para operaciones que modifican datos
- Manejo de excepciones en todos los controladores
- Validaciones de negocio integradas
- Soporte para parámetros opcionales donde corresponde
- Uso de consultas SQL nativas para operaciones complejas