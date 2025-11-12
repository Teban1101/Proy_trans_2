package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.NivelTransporte;
import uniandes.edu.co.proyecto.repositorio.NivelTransporteRepository;

@RestController
@RequestMapping("/nivelesTransporte")
public class NivelTransporteController {

    @Autowired
    private NivelTransporteRepository nivelTransporteRepository;

    @PostMapping("/insertar")
    public String insertarNivelTransporte(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            nivelTransporteRepository.insertarNivelTransporte(id, nombre);
            return "NivelTransporte insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar NivelTransporte: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarNivelTransporte(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            nivelTransporteRepository.actualizarNivelTransporte(id, nombre);
            return "NivelTransporte actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar NivelTransporte: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarNivelTransporte(@PathVariable Integer id) {
        try {
            nivelTransporteRepository.eliminarNivelTransporte(id);
            return "NivelTransporte eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar NivelTransporte: " + e.getMessage();
        }
    }

    @GetMapping("/darNivelesTransporte")
    public List<NivelTransporte> darNivelesTransporte() {
        try {
            return nivelTransporteRepository.darNivelesTransporte();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darNivelTransporte/{id}")
    public NivelTransporte darNivelTransporte(@PathVariable Integer id) {
        try {
            return nivelTransporteRepository.darNivelTransporte(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
