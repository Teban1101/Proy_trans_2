package uniandes.edu.co.proyecto.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@RestController
@RequestMapping("/rfc1")
public class HistorialServiciosController {

    @Autowired
    private ServicioRepository servicioRepository;

    /**
     * RFC1 - NIVEL READ_COMMITTED (Por defecto)
     * Permite lecturas dirty, non-repeatable reads y phantom reads
     * Apropiado para consultas que no requieren consistencia perfecta
     */
    @GetMapping("/historial-usuario-committed/{id_usuario}")
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 30)
    public List<Servicio> obtenerHistorialUsuarioReadCommitted(@PathVariable Integer id_usuario) {
        try {
            System.out.println("[RFC1-READ_COMMITTED] Iniciando consulta de historial para usuario: " + id_usuario);
            
            // Simular lectura con timeout de 30 segundos
            List<Servicio> servicios = servicioRepository.findByIdUsuarioServicio(id_usuario);
            
            System.out.println("[RFC1-READ_COMMITTED] Consulta completada. Servicios encontrados: " + 
                             (servicios != null ? servicios.size() : 0));
            
            return servicios;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener historial (READ_COMMITTED): " + e.getMessage());
        }
    }

    /**
     * RFC1 - NIVEL SERIALIZABLE
     * Proporciona el aislamiento máximo
     * Previene dirty reads, non-repeatable reads y phantom reads
     * Apropiado para consultas críticas que requieren consistencia total
     */
    @GetMapping("/historial-usuario-serializable/{id_usuario}")
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true, timeout = 30)
    public List<Servicio> obtenerHistorialUsuarioSerializable(@PathVariable Integer id_usuario) {
        try {
            System.out.println("[RFC1-SERIALIZABLE] Iniciando consulta de historial para usuario: " + id_usuario);
            
            // Lectura con aislamiento SERIALIZABLE y timeout de 30 segundos
            List<Servicio> servicios = servicioRepository.findByIdUsuarioServicio(id_usuario);
            
            System.out.println("[RFC1-SERIALIZABLE] Consulta completada. Servicios encontrados: " + 
                             (servicios != null ? servicios.size() : 0));
            
            return servicios;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener historial (SERIALIZABLE): " + e.getMessage());
        }
    }

    /**
     * RFC1 - Obtener resumen de servicios por usuario
     * Incluye información de viajes históricos
     */
    @GetMapping("/resumen-usuario/{id_usuario}")
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 30)
    public Map<String, Object> obtenerResumenUsuario(@PathVariable Integer id_usuario) {
        try {
            System.out.println("[RFC1] Iniciando consulta de resumen para usuario: " + id_usuario);
            
            List<Servicio> servicios = servicioRepository.findByIdUsuarioServicio(id_usuario);
            
            long totalServicios = servicios != null ? servicios.size() : 0;
            long costoTotal = 0;
            double distanciaTotal = 0;
            
            if (servicios != null) {
                costoTotal = servicios.stream()
                    .mapToLong(s -> s.getCosto_total() != null ? s.getCosto_total() : 0L)
                    .sum();
                
                distanciaTotal = servicios.stream()
                    .mapToDouble(s -> s.getDistancia_km() != null ? s.getDistancia_km() : 0.0)
                    .sum();
            }
            
            return Map.of(
                "id_usuario", id_usuario,
                "total_servicios", totalServicios,
                "costo_total", costoTotal,
                "distancia_total", String.format("%.2f km", distanciaTotal),
                "nivel_aislamiento", "READ_COMMITTED"
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener resumen: " + e.getMessage());
        }
    }

    /**
     * RFC1 - Obtener servicios completados (con aislamiento estricto)
     */
    @GetMapping("/servicios-completados/{id_usuario}")
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true, timeout = 30)
    public Map<String, Object> obtenerServiciosCompletados(@PathVariable Integer id_usuario) {
        try {
            System.out.println("[RFC1-SERIALIZABLE] Consultando servicios completados para usuario: " + id_usuario);
            
            List<Servicio> servicios = servicioRepository.findByIdUsuarioServicio(id_usuario);
            
            long serviciosCompletados = 0;
            if (servicios != null) {
                serviciosCompletados = servicios.stream()
                    .filter(s -> s.getHora_fin() != null)
                    .count();
            }
            
            return Map.of(
                "id_usuario", id_usuario,
                "servicios_completados", serviciosCompletados,
                "total_servicios", servicios != null ? servicios.size() : 0,
                "nivel_aislamiento", "SERIALIZABLE (máxima consistencia)"
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener servicios completados: " + e.getMessage());
        }
    }
}
