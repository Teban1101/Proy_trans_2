package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Tarifa;
import uniandes.edu.co.proyecto.repositorio.TarifaRepository;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaRepository tarifaRepository;

    @PostMapping("/insertar")
    public String insertarTarifa(@RequestParam Integer id_tarifa, @RequestParam Double valor_por_km,
                                 @RequestParam Integer id_tipo_servicio, @RequestParam(required = false) Integer id_nivel) {
        try {
            tarifaRepository.insertarTarifa(id_tarifa, valor_por_km, id_tipo_servicio, id_nivel);
            return "Tarifa insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar Tarifa: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarTarifa(@RequestParam Integer id_tarifa, @RequestParam Double valor_por_km,
                                   @RequestParam Integer id_tipo_servicio, @RequestParam(required = false) Integer id_nivel) {
        try {
            tarifaRepository.actualizarTarifa(id_tarifa, valor_por_km, id_tipo_servicio, id_nivel);
            return "Tarifa actualizada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar Tarifa: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_tarifa}")
    public String eliminarTarifa(@PathVariable Integer id_tarifa) {
        try {
            tarifaRepository.eliminarTarifa(id_tarifa);
            return "Tarifa eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar Tarifa: " + e.getMessage();
        }
    }

    @GetMapping("/darTarifas")
    public List<Tarifa> darTarifas() {
        try {
            return tarifaRepository.darTarifas();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darTarifa/{id_tarifa}")
    public Tarifa darTarifa(@PathVariable Integer id_tarifa) {
        try {
            return tarifaRepository.darTarifa(id_tarifa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
