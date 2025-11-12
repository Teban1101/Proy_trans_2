package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.TipoVehiculo;
import uniandes.edu.co.proyecto.repositorio.TipoVehiculoRepository;

@RestController
@RequestMapping("/tiposVehiculo")
public class TipoVehiculoController {

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @PostMapping("/insertar")
    public String insertarTipoVehiculo(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            tipoVehiculoRepository.insertarTipoVehiculo(id, nombre);
            return "TipoVehiculo insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar TipoVehiculo: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarTipoVehiculo(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            tipoVehiculoRepository.actualizarTipoVehiculo(id, nombre);
            return "TipoVehiculo actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar TipoVehiculo: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarTipoVehiculo(@PathVariable Integer id) {
        try {
            tipoVehiculoRepository.eliminarTipoVehiculo(id);
            return "TipoVehiculo eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar TipoVehiculo: " + e.getMessage();
        }
    }

    @GetMapping("/darTiposVehiculo")
    public List<TipoVehiculo> darTiposVehiculo() {
        try {
            return tipoVehiculoRepository.darTiposVehiculo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darTipoVehiculo/{id}")
    public TipoVehiculo darTipoVehiculo(@PathVariable Integer id) {
        try {
            return tipoVehiculoRepository.darTipoVehiculo(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
