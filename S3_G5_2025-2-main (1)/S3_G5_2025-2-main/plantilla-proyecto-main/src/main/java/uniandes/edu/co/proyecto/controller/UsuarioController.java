package uniandes.edu.co.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Usuario;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/insertar")
    public String insertarUsuario(@RequestParam Integer cedula, @RequestParam String nombre,
                                  @RequestParam(required = false) String correo, @RequestParam String celular) {
        try {
            usuarioRepository.insertarUsuario(cedula, nombre, correo, celular);
            return "Usuario insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar usuario: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarUsuario(@RequestParam Integer cedula, @RequestParam String nombre,
                                    @RequestParam(required = false) String correo, @RequestParam String celular) {
        try {
            usuarioRepository.actualizarUsuario(cedula, nombre, correo, celular);
            return "Usuario actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar usuario: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{cedula}")
    public String eliminarUsuario(@PathVariable Integer cedula) {
        try {
            usuarioRepository.eliminarUsuario(cedula);
            return "Usuario eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar usuario: " + e.getMessage();
        }
    }

    @GetMapping("/darUsuarios")
    public List<Usuario> darUsuarios() {
        try {
            return usuarioRepository.darUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darUsuario/{cedula}")
    public Usuario darUsuario(@PathVariable Integer cedula) {
        try {
            return usuarioRepository.darUsuario(cedula);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
