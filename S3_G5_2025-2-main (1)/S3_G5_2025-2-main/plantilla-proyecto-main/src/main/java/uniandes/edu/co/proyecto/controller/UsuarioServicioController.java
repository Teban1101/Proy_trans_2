package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.UsuarioServicio;
import uniandes.edu.co.proyecto.repositorio.UsuarioServicioRepository;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;

@RestController
@RequestMapping("/usuariosServicio")
public class UsuarioServicioController {

    @Autowired
    private UsuarioServicioRepository usuarioServicioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // RF2 - Registrar un usuario de servicios (usuario + usuario_servicio)
    @PostMapping("/registrar")
    public String registrarUsuarioServicio(@RequestParam Integer cedula, 
                                          @RequestParam String nombre,
                                          @RequestParam(required = false) String correo, 
                                          @RequestParam String celular) {
        try {
            // Primero registrar el usuario
            usuarioRepository.insertarUsuario(cedula, nombre, correo, celular);
            // Luego registrar como usuario de servicios
            usuarioServicioRepository.insertarUsuarioServicio(cedula);
            return "Usuario de servicios registrado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al registrar usuario de servicios: " + e.getMessage();
        }
    }

    @PostMapping("/insertar")
    public String insertarUsuarioServicio(@RequestParam Integer id_usuario) {
        try {
            usuarioServicioRepository.insertarUsuarioServicio(id_usuario);
            return "UsuarioServicio insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar UsuarioServicio: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_usuario}")
    public String eliminarUsuarioServicio(@PathVariable Integer id_usuario) {
        try {
            usuarioServicioRepository.eliminarUsuarioServicio(id_usuario);
            return "UsuarioServicio eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar UsuarioServicio: " + e.getMessage();
        }
    }

    @GetMapping("/darUsuariosServicio")
    public List<UsuarioServicio> darUsuariosServicio() {
        try {
            return usuarioServicioRepository.darUsuariosServicio();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darUsuarioServicio/{id_usuario}")
    public UsuarioServicio darUsuarioServicio(@PathVariable Integer id_usuario) {
        try {
            return usuarioServicioRepository.darUsuarioServicio(id_usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
