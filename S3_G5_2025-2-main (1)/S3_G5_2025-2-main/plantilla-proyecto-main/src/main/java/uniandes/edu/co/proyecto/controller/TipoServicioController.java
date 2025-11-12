package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.TipoServicio;
import uniandes.edu.co.proyecto.repositorio.TipoServicioRepository;

@RestController
@RequestMapping("/tiposServicio")
public class TipoServicioController {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @PostMapping("/insertar")
    public String insertarTipoServicio(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            tipoServicioRepository.insertarTipoServicio(id, nombre);
            return "TipoServicio insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar TipoServicio: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarTipoServicio(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            tipoServicioRepository.actualizarTipoServicio(id, nombre);
            return "TipoServicio actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar TipoServicio: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarTipoServicio(@PathVariable Integer id) {
        try {
            tipoServicioRepository.eliminarTipoServicio(id);
            return "TipoServicio eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar TipoServicio: " + e.getMessage();
        }
    }

    @GetMapping("/darTiposServicio")
    public List<TipoServicio> darTiposServicio() {
        try {
            return tipoServicioRepository.darTiposServicio();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darTipoServicio/{id}")
    public TipoServicio darTipoServicio(@PathVariable Integer id) {
        try {
            return tipoServicioRepository.darTipoServicio(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
