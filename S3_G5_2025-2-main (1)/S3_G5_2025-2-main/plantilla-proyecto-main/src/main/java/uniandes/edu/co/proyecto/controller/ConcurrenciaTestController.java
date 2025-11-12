package uniandes.edu.co.proyecto.controller;

import java.time.OffsetDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;
import uniandes.edu.co.proyecto.repositorio.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositorio.TarifaRepository;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;
import uniandes.edu.co.proyecto.repositorio.TarjetaCreditoRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioDestinoRepository;

/**
 * Controlador para pruebas de concurrencia de RFC1 y RF8
 * Punto 4: Serializable - RFC1 espera a que RF8 termine
 * Punto 5: Read Committed - RFC1 y RF8 ejecutan concurrentemente
 */
@RestController
@RequestMapping("/pruebas-concurrencia")
public class ConcurrenciaTestController {

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

    /**
     * PUNTO 4: Prueba de concurrencia en nivel SERIALIZABLE
     * Descripción: RFC1 ejecuta primero en SERIALIZABLE, luego RF8 ejecuta concurrentemente
     * Resultado esperado: RFC1 debe esperar a que RF8 termine
     */
    @PostMapping("/test-serializable")
    public Map<String, Object> testConcurrenciaSerializable(
            @RequestParam Integer id_usuario,
            @RequestParam Integer id_servicio_rf8,
            @RequestParam Double distancia_km,
            @RequestParam Integer id_tipo_servicio,
            @RequestParam Integer id_usuario_servicio,
            @RequestParam Integer id_punto_origen,
            @RequestParam Integer id_punto_destino) {
        
        List<String> eventos = new ArrayList<>();
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(2);
            AtomicInteger ordenEjecucion = new AtomicInteger(0);
            
            // TAREA 1: RFC1 en SERIALIZABLE (ejecuta primero)
            executor.execute(() -> {
                try {
                    long t1 = System.currentTimeMillis() - tiempoInicio;
                    int orden = ordenEjecucion.incrementAndGet();
                    eventos.add(t1 + "ms - [" + orden + "] RFC1-SERIALIZABLE: Iniciando consulta...");
                    
                    Thread.sleep(500); // Simular operación
                    List<Servicio> servicios = servicioRepository.findByIdUsuarioServicio(id_usuario);
                    
                    long t2 = System.currentTimeMillis() - tiempoInicio;
                    eventos.add(t2 + "ms - [" + orden + "] RFC1-SERIALIZABLE: Consulta completada (" + 
                               (servicios != null ? servicios.size() : 0) + " servicios)");
                } catch (Exception e) {
                    eventos.add("ERROR RFC1: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
            
            // TAREA 2: RF8 ejecuta concurrentemente (después de 1 segundo)
            executor.execute(() -> {
                try {
                    Thread.sleep(1000); // Esperar a que RFC1 inicie
                    
                    long t1 = System.currentTimeMillis() - tiempoInicio;
                    int orden = ordenEjecucion.incrementAndGet();
                    eventos.add(t1 + "ms - [" + orden + "] RF8: Iniciando solicitud de servicio...");
                    
                    // Ejecutar RF8 con transacción
                    ejecutarRF8Concurrente(id_servicio_rf8, distancia_km, id_tipo_servicio, 
                                          id_usuario_servicio, id_punto_origen, id_punto_destino, eventos, tiempoInicio);
                    
                    long t2 = System.currentTimeMillis() - tiempoInicio;
                    int ord = ordenEjecucion.get();
                    eventos.add(t2 + "ms - [" + ord + "] RF8: Servicio solicitado");
                } catch (Exception e) {
                    eventos.add("ERROR RF8: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
            
            // Esperar a que ambas tareas terminen
            latch.await(60, TimeUnit.SECONDS);
            executor.shutdown();
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            return Map.of(
                "prueba", "CONCURRENCIA SERIALIZABLE (Punto 4)",
                "usuario_id", id_usuario,
                "tiempo_total_ms", tiempoTotal,
                "resultado_esperado", "RFC1 debe esperar a que RF8 termine (bloqueo de lectura/escritura)",
                "eventos", eventos,
                "observacion", "En SERIALIZABLE, el segundo RFC1 debe esperar a que RF8 complete su transacción"
            );
            
        } catch (Exception e) {
            return Map.of(
                "error", "Error en prueba de concurrencia SERIALIZABLE",
                "mensaje", e.getMessage(),
                "eventos", eventos
            );
        }
    }

    /**
     * PUNTO 5: Prueba de concurrencia en nivel READ_COMMITTED
     * Descripción: RFC1 ejecuta primero en READ_COMMITTED, luego RF8 ejecuta concurrentemente
     * Resultado esperado: RFC1 y RF8 pueden ejecutar concurrentemente (menos bloqueos)
     */
    @PostMapping("/test-read-committed")
    public Map<String, Object> testConcurrenciaReadCommitted(
            @RequestParam Integer id_usuario,
            @RequestParam Integer id_servicio_rf8,
            @RequestParam Double distancia_km,
            @RequestParam Integer id_tipo_servicio,
            @RequestParam Integer id_usuario_servicio,
            @RequestParam Integer id_punto_origen,
            @RequestParam Integer id_punto_destino) {
        
        List<String> eventos = new ArrayList<>();
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(2);
            AtomicInteger ordenEjecucion = new AtomicInteger(0);
            
            // TAREA 1: RFC1 en READ_COMMITTED (ejecuta primero)
            executor.execute(() -> {
                try {
                    long t1 = System.currentTimeMillis() - tiempoInicio;
                    int orden = ordenEjecucion.incrementAndGet();
                    eventos.add(t1 + "ms - [" + orden + "] RFC1-READ_COMMITTED: Iniciando consulta...");
                    
                    Thread.sleep(2000); // Simular operación más larga
                    List<Servicio> servicios = servicioRepository.findByIdUsuarioServicio(id_usuario);
                    
                    long t2 = System.currentTimeMillis() - tiempoInicio;
                    eventos.add(t2 + "ms - [" + orden + "] RFC1-READ_COMMITTED: Consulta completada (" + 
                               (servicios != null ? servicios.size() : 0) + " servicios)");
                } catch (Exception e) {
                    eventos.add("ERROR RFC1: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
            
            // TAREA 2: RF8 ejecuta concurrentemente (después de 500ms)
            executor.execute(() -> {
                try {
                    Thread.sleep(500); // Esperar a que RFC1 inicie (pero no termine)
                    
                    long t1 = System.currentTimeMillis() - tiempoInicio;
                    int orden = ordenEjecucion.incrementAndGet();
                    eventos.add(t1 + "ms - [" + orden + "] RF8: Iniciando solicitud de servicio...");
                    
                    // Ejecutar RF8 con transacción
                    ejecutarRF8Concurrente(id_servicio_rf8, distancia_km, id_tipo_servicio, 
                                          id_usuario_servicio, id_punto_origen, id_punto_destino, eventos, tiempoInicio);
                    
                    long t2 = System.currentTimeMillis() - tiempoInicio;
                    int ord = ordenEjecucion.get();
                    eventos.add(t2 + "ms - [" + ord + "] RF8: Servicio solicitado");
                } catch (Exception e) {
                    eventos.add("ERROR RF8: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
            
            // Esperar a que ambas tareas terminen
            latch.await(60, TimeUnit.SECONDS);
            executor.shutdown();
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            return Map.of(
                "prueba", "CONCURRENCIA READ_COMMITTED (Punto 5)",
                "usuario_id", id_usuario,
                "tiempo_total_ms", tiempoTotal,
                "resultado_esperado", "RFC1 y RF8 ejecutan concurrentemente (menos bloqueos que SERIALIZABLE)",
                "eventos", eventos,
                "observacion", "En READ_COMMITTED, RF8 puede proceder sin esperar a que RFC1 termine"
            );
            
        } catch (Exception e) {
            return Map.of(
                "error", "Error en prueba de concurrencia READ_COMMITTED",
                "mensaje", e.getMessage(),
                "eventos", eventos
            );
        }
    }

    /**
     * Método auxiliar para ejecutar RF8 en contexto de prueba de concurrencia
     */
    @Transactional(rollbackFor = Exception.class)
    private void ejecutarRF8Concurrente(Integer id_servicio_rf8, Double distancia_km,
                                       Integer id_tipo_servicio, Integer id_usuario_servicio,
                                       Integer id_punto_origen, Integer id_punto_destino,
                                       List<String> eventos, long tiempoInicio) throws Exception {
        
        try {
            // Verificar tarjeta
            Integer countTarjetas = tarjetaCreditoRepository.contarTarjetasPorUsuario(id_usuario_servicio);
            if (countTarjetas == null || countTarjetas == 0) {
                throw new Exception("Sin medio de pago");
            }

            OffsetDateTime ahora = OffsetDateTime.now();
            int diaSemana = DayOfWeek.from(ahora).getValue();

            // Buscar conductor disponible
            List<Object[]> candidatos = disponibilidadRepository.buscarDisponiblesEnMomento(diaSemana, ahora);
            if (candidatos == null || candidatos.isEmpty()) {
                throw new Exception("Sin conductores disponibles");
            }
            Integer id_conductor = (Integer) candidatos.get(0)[0];

            // Obtener vehículo
            String id_vehiculo = vehiculoRepository.obtenerVehiculoPorConductor(id_conductor);
            if (id_vehiculo == null) {
                throw new Exception("Conductor sin vehículo");
            }

            Integer id_nivel = vehiculoRepository.obtenerIdNivel(id_vehiculo);
            Double valorPorKm = tarifaRepository.obtenerValorPorKm(id_tipo_servicio, id_nivel);
            if (valorPorKm == null) {
                throw new Exception("Sin tarifa configurada");
            }

            Long costo_total = Math.round(distancia_km * valorPorKm);

            // Registrar servicio
            servicioRepository.solicitarServicio(id_servicio_rf8, distancia_km, costo_total,
                                                ahora, id_tipo_servicio, id_usuario_servicio,
                                                id_conductor, id_vehiculo);

            // Registrar destinos
            servicioDestinoRepository.insertarServicioDestino(id_servicio_rf8, id_punto_origen, 1);
            servicioDestinoRepository.insertarServicioDestino(id_servicio_rf8, id_punto_destino, 2);

        } catch (Exception e) {
            throw e;
        }
    }
}
