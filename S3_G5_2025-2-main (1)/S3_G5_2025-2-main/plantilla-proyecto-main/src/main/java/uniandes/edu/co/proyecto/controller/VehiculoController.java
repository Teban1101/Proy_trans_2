package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Vehiculo;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @PostMapping("/insertar")
    public String insertarVehiculo(@RequestParam String placa, @RequestParam String marca,
                                   @RequestParam String modelo, @RequestParam String color,
                                   @RequestParam Integer capacidad, @RequestParam Integer id_usuario,
                                   @RequestParam Integer id_tipo_vehiculo, @RequestParam(required = false) Integer id_nivel,
                                   @RequestParam Integer id_ciudad) {
        try {
            vehiculoRepository.insertarVehiculo(placa, marca, modelo, color, capacidad, id_usuario, id_tipo_vehiculo, id_nivel, id_ciudad);
            return "Vehiculo insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar Vehiculo: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarVehiculo(@RequestParam String placa, @RequestParam String marca,
                                     @RequestParam String modelo, @RequestParam String color,
                                     @RequestParam Integer capacidad, @RequestParam Integer id_usuario,
                                     @RequestParam Integer id_tipo_vehiculo, @RequestParam(required = false) Integer id_nivel,
                                     @RequestParam Integer id_ciudad) {
        try {
            vehiculoRepository.actualizarVehiculo(placa, marca, modelo, color, capacidad, id_usuario, id_tipo_vehiculo, id_nivel, id_ciudad);
            return "Vehiculo actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar Vehiculo: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{placa}")
    public String eliminarVehiculo(@PathVariable String placa) {
        try {
            vehiculoRepository.eliminarVehiculo(placa);
            return "Vehiculo eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar Vehiculo: " + e.getMessage();
        }
    }

    @GetMapping("/darVehiculos")
    public List<Vehiculo> darVehiculos() {
        try {
            return vehiculoRepository.darVehiculos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darVehiculo/{placa}")
    public Vehiculo darVehiculo(@PathVariable String placa) {
        try {
            return vehiculoRepository.darVehiculo(placa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/conductor/{id}")
    public List<Vehiculo> darVehiculosPorConductor(@PathVariable Integer id) {
        try {
            return vehiculoRepository.darVehiculosPorUsuario(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
