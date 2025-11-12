package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.UsuarioConductor;
import uniandes.edu.co.proyecto.repositorio.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;

@RestController
@RequestMapping("/usuariosConductor")
public class UsuarioConductorController {

    @Autowired
    private UsuarioConductorRepository usuarioConductorRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // RF3 - Registrar un usuario conductor (usuario + usuario_conductor)
    @PostMapping("/registrar")
    public String registrarUsuarioConductor(@RequestParam Integer cedula, 
                                           @RequestParam String nombre,
                                           @RequestParam(required = false) String correo, 
                                           @RequestParam String celular) {
        try {
            // Primero registrar el usuario
            usuarioRepository.insertarUsuario(cedula, nombre, correo, celular);
            // Luego registrar como usuario conductor
            usuarioConductorRepository.insertarUsuarioConductor(cedula);
            return "Usuario conductor registrado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al registrar usuario conductor: " + e.getMessage();
        }
    }

    @PostMapping("/insertar")
    public String insertarUsuarioConductor(@RequestParam Integer id_usuario) {
        try {
            usuarioConductorRepository.insertarUsuarioConductor(id_usuario);
            return "UsuarioConductor insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar UsuarioConductor: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_usuario}")
    public String eliminarUsuarioConductor(@PathVariable Integer id_usuario) {
        try {
            usuarioConductorRepository.eliminarUsuarioConductor(id_usuario);
            return "UsuarioConductor eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar UsuarioConductor: " + e.getMessage();
        }
    }

    @GetMapping("/darUsuariosConductor")
    public List<UsuarioConductor> darUsuariosConductor() {
        try {
            return usuarioConductorRepository.darUsuariosConductor();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darUsuarioConductor/{id_usuario}")
    public UsuarioConductor darUsuarioConductor(@PathVariable Integer id_usuario) {
        try {
            return usuarioConductorRepository.darUsuarioConductor(id_usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
