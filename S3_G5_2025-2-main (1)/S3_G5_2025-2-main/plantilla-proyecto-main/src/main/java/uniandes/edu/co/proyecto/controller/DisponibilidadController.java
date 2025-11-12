package uniandes.edu.co.proyecto.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Disponibilidad;
import uniandes.edu.co.proyecto.repositorio.DisponibilidadRepository;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    /* ========== RF5: insertar SIN superposición ========== */
    @PostMapping("/insertar")
    public String insertarDisponibilidad(@RequestParam Integer id_disponibilidad, @RequestParam Integer dia_semana,
                                         @RequestParam String hora_inicio, @RequestParam String hora_fin,
                                         @RequestParam Integer id_usuario, @RequestParam String id_vehiculo) {
        try {
            // Parse incoming ISO datetime strings to OffsetDateTime. Return 400-like message if parse fails.
            OffsetDateTime hi;
            OffsetDateTime hf;
            try {
                hi = OffsetDateTime.parse(hora_inicio);
                hf = OffsetDateTime.parse(hora_fin);
            } catch (Exception pe) {
                pe.printStackTrace();
                return "Error: formato de fecha/hora inválido. Use ISO-8601 con zona, por ejemplo 2025-10-29T08:00:00Z";
            }

            List<Disponibilidad> choque = disponibilidadRepository.verificarSuperposicion(
                id_usuario, id_vehiculo, dia_semana, hi, hf
            );
            if (!choque.isEmpty()) {
                return "Error: la disponibilidad se superpone con otra existente para el mismo conductor/vehículo.";
            }

            disponibilidadRepository.insertarDisponibilidad(id_disponibilidad, dia_semana, hi, hf, id_usuario, id_vehiculo);
            return "Disponibilidad insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar Disponibilidad: " + e.getMessage();
        }
    }

    /* ========== RF6: actualizar SIN superposición ========== */
    @PutMapping("/actualizar")
    public String actualizarDisponibilidad(@RequestParam Integer id_disponibilidad, @RequestParam Integer dia_semana,
                                           @RequestParam String hora_inicio, @RequestParam String hora_fin,
                                           @RequestParam Integer id_usuario, @RequestParam String id_vehiculo) {
        try {
            OffsetDateTime hi;
            OffsetDateTime hf;
            try {
                hi = OffsetDateTime.parse(hora_inicio);
                hf = OffsetDateTime.parse(hora_fin);
            } catch (Exception pe) {
                pe.printStackTrace();
                return "Error: formato de fecha/hora inválido. Use ISO-8601 con zona, por ejemplo 2025-10-29T08:00:00Z";
            }

            List<Disponibilidad> choque = disponibilidadRepository.verificarSuperposicion(
                id_usuario, id_vehiculo, dia_semana, hi, hf
            );
            boolean hayOtro = choque.stream().anyMatch(d -> !d.getId_disponibilidad().equals(id_disponibilidad));
            if (hayOtro) {
                return "Error: la nueva disponibilidad se superpone con otra existente para el mismo conductor/vehículo.";
            }

            disponibilidadRepository.actualizarDisponibilidad(id_disponibilidad, dia_semana, hi, hf, id_usuario, id_vehiculo);
            return "Disponibilidad actualizada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar Disponibilidad: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_disponibilidad}")
    public String eliminarDisponibilidad(@PathVariable Integer id_disponibilidad) {
        try {
            disponibilidadRepository.eliminarDisponibilidad(id_disponibilidad);
            return "Disponibilidad eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar Disponibilidad: " + e.getMessage();
        }
    }

    @GetMapping("/darDisponibilidades")
    public List<Disponibilidad> darDisponibilidades() {
        try {
            return disponibilidadRepository.darDisponibilidades();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darDisponibilidad/{id_disponibilidad}")
    public Disponibilidad darDisponibilidad(@PathVariable Integer id_disponibilidad) {
        try {
            return disponibilidadRepository.darDisponibilidad(id_disponibilidad);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
