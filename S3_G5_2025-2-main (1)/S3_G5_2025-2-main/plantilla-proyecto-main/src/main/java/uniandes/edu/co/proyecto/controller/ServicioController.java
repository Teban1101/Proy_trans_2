package uniandes.edu.co.proyecto.controller;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioDestinoRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;
import uniandes.edu.co.proyecto.repositorio.TarifaRepository;
import uniandes.edu.co.proyecto.repositorio.TarjetaCreditoRepository;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private ServicioDestinoRepository servicioDestinoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    // RF9: Devuelve todos los servicios
    @GetMapping("/listar")
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    // RF9: Finaliza un servicio (establece hora_fin a ahora)
    @PutMapping("/finalizar/{id_servicio}")
    public String finalizarServicio(@PathVariable Integer id_servicio) {
        Servicio servicio = servicioRepository.findById(id_servicio).orElse(null);
        if (servicio == null) return "Servicio no encontrado";
        servicio.setHora_fin(OffsetDateTime.now());
        servicioRepository.save(servicio);
        return "Servicio finalizado";
    }

    @PostMapping("/insertar")
    public String insertarServicio(@RequestParam Integer id_servicio, @RequestParam Double distancia_km,
                                   @RequestParam Long costo_total, @RequestParam OffsetDateTime hora_inicio,
                                   @RequestParam(required = false) OffsetDateTime hora_fin,
                                   @RequestParam Integer id_tipo_servicio, @RequestParam Integer id_usuario_servicio,
                                   @RequestParam(required = false) Integer id_conductor,
                                   @RequestParam(required = false) String id_vehiculo) {
        try {
            servicioRepository.insertarServicio(id_servicio, distancia_km, costo_total, hora_inicio, hora_fin,
                                                id_tipo_servicio, id_usuario_servicio, id_conductor, id_vehiculo);
            return "Servicio insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar Servicio: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarServicio(@RequestParam Integer id_servicio, @RequestParam Double distancia_km,
                                     @RequestParam Long costo_total, @RequestParam OffsetDateTime hora_inicio,
                                     @RequestParam(required = false) OffsetDateTime hora_fin,
                                     @RequestParam Integer id_tipo_servicio, @RequestParam Integer id_usuario_servicio,
                                     @RequestParam(required = false) Integer id_conductor,
                                     @RequestParam(required = false) String id_vehiculo) {
        try {
            servicioRepository.actualizarServicio(id_servicio, distancia_km, costo_total, hora_inicio, hora_fin,
                                                  id_tipo_servicio, id_usuario_servicio, id_conductor, id_vehiculo);
            return "Servicio actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar Servicio: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_servicio}")
    public String eliminarServicio(@PathVariable Integer id_servicio) {
        try {
            servicioRepository.eliminarServicio(id_servicio);
            return "Servicio eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar Servicio: " + e.getMessage();
        }
    }

    // ...existing code...

        @GetMapping("/listarServiciosSimple")
    public List<Map<String, Object>> listarServiciosSimple() {
        try {
            return servicioRepository.listarServiciosSimple();
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }


    @GetMapping("/darServicio/{id_servicio}")
    public Servicio darServicio(@PathVariable Integer id_servicio) {
        try {
            return servicioRepository.darServicio(id_servicio);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* ==================== RFCs ==================== */

    @GetMapping("/rfc1")
    public List<Object[]> rfc1HistoricoPorUsuario(@RequestParam("usuario") Integer idUsuario) {
        try {
            return servicioRepository.rfc1HistoricoPorUsuario(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/rfc2")
    public List<Object[]> rfc2Top20Conductores() {
        try {
            return servicioRepository.rfc2Top20Conductores();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/rfc3")
    public List<Object[]> rfc3DetallePorServicio(@RequestParam("comisionPct") Double comisionPct) {
        try {
            return servicioRepository.rfc3DetallePorServicio(comisionPct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/rfc3/agregado")
    public List<Object[]> rfc3AgregadoPorVehiculo(@RequestParam("comisionPct") Double comisionPct) {
        try {
            return servicioRepository.rfc3AgregadoPorVehiculo(comisionPct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/rfc4")
    public List<Object[]> rfc4UsoPorCiudadYRango(@RequestParam("ciudad") String ciudad,
                                                 @RequestParam("ini") OffsetDateTime fechaInicio,
                                                 @RequestParam("fin") OffsetDateTime fechaFin) {
        try {
            return servicioRepository.rfc4UsoPorCiudadYRango(ciudad, fechaInicio, fechaFin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/solicitarAuto")
    @Transactional(rollbackFor = Exception.class)
    public String solicitarServicioAuto(@RequestParam Integer id_servicio,
                                        @RequestParam Double distancia_km,
                                        @RequestParam Integer id_tipo_servicio,
                                        @RequestParam Integer id_usuario_servicio,
                                        @RequestParam Integer id_punto_origen,
                                        @RequestParam Integer id_punto_destino,
                                        @RequestParam(required = false) Integer id_conductor) {
        try {
            // RF8: Verificar que el usuario tiene un medio de pago registrado
            Integer countTarjetas = tarjetaCreditoRepository.contarTarjetasPorUsuario(id_usuario_servicio);
            if (countTarjetas == null || countTarjetas == 0) {
                throw new Exception("El usuario no tiene un medio de pago registrado disponible.");
            }

            OffsetDateTime ahora = OffsetDateTime.now();
            int diaSemana = DayOfWeek.from(ahora).getValue(); 

            // RF8: Si no viene id_conductor del frontend, buscar uno disponible
            if (id_conductor == null) {
                List<Object[]> candidatos = disponibilidadRepository.buscarDisponiblesEnMomento(diaSemana, ahora);
                if (candidatos == null || candidatos.isEmpty()) {
                    throw new Exception("No hay conductores disponibles en este momento.");
                }
                id_conductor = (Integer) candidatos.get(0)[0];
            }
            
            // Obtener vehículo del conductor
            String id_vehiculo = vehiculoRepository.obtenerVehiculoPorConductor(id_conductor);
            if (id_vehiculo == null) {
                throw new Exception("El conductor no tiene vehículo asignado.");
            }

            Integer id_nivel = vehiculoRepository.obtenerIdNivel(id_vehiculo);

            // RF8: Calcular la tarifa
            Double valorPorKm = tarifaRepository.obtenerValorPorKm(id_tipo_servicio, id_nivel);
            if (valorPorKm == null) {
                throw new Exception("No existe tarifa configurada para el tipo de servicio y nivel seleccionados.");
            }
            Long costo_total = Math.round(distancia_km * valorPorKm);

            // RF8: Registrar el inicio del viaje y asignar conductor (OPERACION 1)
            servicioRepository.solicitarServicio(id_servicio, distancia_km, costo_total,
                                                 ahora, id_tipo_servicio, id_usuario_servicio,
                                                 id_conductor, id_vehiculo);

            // OPERACION 2: Registrar punto de origen
            servicioDestinoRepository.insertarServicioDestino(id_servicio, id_punto_origen, 1);
            
            // OPERACION 3: Registrar punto de destino
            servicioDestinoRepository.insertarServicioDestino(id_servicio, id_punto_destino, 2);

            return "Servicio solicitado correctamente (auto-asignado). ID: " + id_servicio +
                   " | Conductor: " + id_conductor + " | Vehículo: " + id_vehiculo +
                   " | Costo: " + costo_total;
        } catch (Exception e) {
            e.printStackTrace();
            // La transacción se revierte automáticamente
            return "Error al solicitar servicio: " + e.getMessage();
        }
    }

    @PutMapping("/terminar/{id_servicio}")
    public String terminarServicio(@PathVariable Integer id_servicio) {
        try {
            OffsetDateTime ahora = OffsetDateTime.now();
            servicioRepository.terminarServicio(id_servicio, ahora);
            return "Servicio con ID " + id_servicio + " marcado como terminado";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al terminar servicio: " + e.getMessage();
        }
    }
}
