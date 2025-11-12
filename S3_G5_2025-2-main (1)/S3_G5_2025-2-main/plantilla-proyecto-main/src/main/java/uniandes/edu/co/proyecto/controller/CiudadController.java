package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Ciudad;
import uniandes.edu.co.proyecto.repositorio.CiudadRepository;

@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private CiudadRepository ciudadRepository;

    @PostMapping("/insertar")
    public String insertarCiudad(@RequestParam Integer id_ciudad, @RequestParam String nombre) {
        try {
            ciudadRepository.insertarCiudad(id_ciudad, nombre);
            return "Ciudad insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar ciudad: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarCiudad(@RequestParam Integer id_ciudad, @RequestParam String nombre) {
        try {
            ciudadRepository.actualizarCiudad(id_ciudad, nombre);
            return "Ciudad actualizada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar ciudad: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_ciudad}")
    public String eliminarCiudad(@PathVariable Integer id_ciudad) {
        try {
            ciudadRepository.eliminarCiudad(id_ciudad);
            return "Ciudad eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar ciudad: " + e.getMessage();
        }
    }

    @GetMapping("/darCiudades")
    public List<Ciudad> darCiudades() {
        try {
            return ciudadRepository.darCiudades();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darCiudad/{id_ciudad}")
    public Ciudad darCiudad(@PathVariable Integer id_ciudad) {
        try {
            return ciudadRepository.darCiudad(id_ciudad);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

