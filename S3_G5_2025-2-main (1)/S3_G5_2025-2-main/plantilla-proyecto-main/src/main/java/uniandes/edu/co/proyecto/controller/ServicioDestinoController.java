package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.ServicioDestino;
import uniandes.edu.co.proyecto.repositorio.ServicioDestinoRepository;

@RestController
@RequestMapping("/serviciosDestino")
public class ServicioDestinoController {

    @Autowired
    private ServicioDestinoRepository servicioDestinoRepository;

    @PostMapping("/insertar")
    public String insertarServicioDestino(@RequestParam Integer id_servicio,
                                          @RequestParam Integer id_punto,
                                          @RequestParam Integer orden) {
        try {
            servicioDestinoRepository.insertarServicioDestino(id_servicio, id_punto, orden);
            return "ServicioDestino insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar ServicioDestino: " + e.getMessage();
        }
    }

    @PutMapping("/actualizarOrden")
    public String actualizarOrden(@RequestParam Integer id_servicio,
                                  @RequestParam Integer id_punto,
                                  @RequestParam Integer orden,
                                  @RequestParam Integer nuevo_orden) {
        try {
            servicioDestinoRepository.actualizarOrden(id_servicio, id_punto, orden, nuevo_orden);
            return "Orden de ServicioDestino actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar ServicioDestino: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar")
    public String eliminarServicioDestino(@RequestParam Integer id_servicio,
                                          @RequestParam Integer id_punto,
                                          @RequestParam Integer orden) {
        try {
            servicioDestinoRepository.eliminarServicioDestino(id_servicio, id_punto, orden);
            return "ServicioDestino eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar ServicioDestino: " + e.getMessage();
        }
    }

    @GetMapping("/darServiciosDestino")
    public List<ServicioDestino> darServiciosDestino() {
        try {
            return servicioDestinoRepository.darServiciosDestino();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darServicioDestino")
    public ServicioDestino darServicioDestino(@RequestParam Integer id_servicio,
                                              @RequestParam Integer id_punto,
                                              @RequestParam Integer orden) {
        try {
            return servicioDestinoRepository.darServicioDestino(id_servicio, id_punto, orden);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
