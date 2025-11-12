package uniandes.edu.co.proyecto.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.TarjetaCredito;
import uniandes.edu.co.proyecto.repositorio.TarjetaCreditoRepository;

@RestController
@RequestMapping("/tarjetasCredito")
public class TarjetaCreditoController {

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    @PostMapping("/insertar")
    public String insertarTarjeta(@RequestParam String numero, @RequestParam String nombre_tarjeta,
                                  @RequestParam LocalDate vencimiento, @RequestParam Integer cvv,
                                  @RequestParam Integer id_usuario) {
        try {
            tarjetaCreditoRepository.insertarTarjeta(numero, nombre_tarjeta, vencimiento, cvv, id_usuario);
            return "Tarjeta insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar Tarjeta: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarTarjeta(@RequestParam String numero, @RequestParam String nombre_tarjeta,
                                    @RequestParam LocalDate vencimiento, @RequestParam Integer cvv,
                                    @RequestParam Integer id_usuario) {
        try {
            tarjetaCreditoRepository.actualizarTarjeta(numero, nombre_tarjeta, vencimiento, cvv, id_usuario);
            return "Tarjeta actualizada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar Tarjeta: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{numero}")
    public String eliminarTarjeta(@PathVariable String numero) {
        try {
            tarjetaCreditoRepository.eliminarTarjeta(numero);
            return "Tarjeta eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar Tarjeta: " + e.getMessage();
        }
    }

    @GetMapping("/darTarjetas")
    public List<TarjetaCredito> darTarjetas() {
        try {
            return tarjetaCreditoRepository.darTarjetas();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darTarjeta/{numero}")
    public TarjetaCredito darTarjeta(@PathVariable String numero) {
        try {
            return tarjetaCreditoRepository.darTarjeta(numero);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
