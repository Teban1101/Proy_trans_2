package uniandes.edu.co.proyecto.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Revision;
import uniandes.edu.co.proyecto.repositorio.RevisionRepository;

@RestController
@RequestMapping("/revisiones")
public class RevisionController {

    @Autowired
    private RevisionRepository revisionRepository;

    // RF10 - Dejar una revisión por parte del usuario de servicios
    @PostMapping("/usuario-servicio")
    public String dejarRevisionUsuarioServicio(@RequestParam Integer id_revision, 
                                              @RequestParam Integer calificacion, 
                                              @RequestParam(required = false) String comentario,
                                              @RequestParam Integer id_servicio,
                                              @RequestParam Integer id_usuario) {
        try {
            LocalDate fechaActual = LocalDate.now();
            revisionRepository.insertarRevision(id_revision, "Usuario", calificacion, comentario, 
                                               fechaActual, id_servicio, id_usuario);
            return "Revisión del usuario de servicios insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar revisión del usuario de servicios: " + e.getMessage();
        }
    }

    // RF11 - Dejar una revisión por parte del usuario conductor
    @PostMapping("/usuario-conductor")
    public String dejarRevisionUsuarioConductor(@RequestParam Integer id_revision, 
                                               @RequestParam Integer calificacion, 
                                               @RequestParam(required = false) String comentario,
                                               @RequestParam Integer id_servicio,
                                               @RequestParam Integer id_usuario) {
        try {
            LocalDate fechaActual = LocalDate.now();
            revisionRepository.insertarRevision(id_revision, "Conductor", calificacion, comentario, 
                                               fechaActual, id_servicio, id_usuario);
            return "Revisión del usuario conductor insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar revisión del usuario conductor: " + e.getMessage();
        }
    }

    @PostMapping("/insertar")
    public String insertarRevision(@RequestParam Integer id_revision, @RequestParam String rol_autor,
                                   @RequestParam Integer calificacion, @RequestParam(required = false) String comentario,
                                   @RequestParam LocalDate fecha, @RequestParam Integer id_servicio,
                                   @RequestParam Integer id_usuario) {
        try {
            revisionRepository.insertarRevision(id_revision, rol_autor, calificacion, comentario, fecha, id_servicio, id_usuario);
            return "Revision insertada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar Revision: " + e.getMessage();
        }
    }

    @PutMapping("/actualizar")
    public String actualizarRevision(@RequestParam Integer id_revision, @RequestParam String rol_autor,
                                     @RequestParam Integer calificacion, @RequestParam(required = false) String comentario,
                                     @RequestParam LocalDate fecha, @RequestParam Integer id_servicio,
                                     @RequestParam Integer id_usuario) {
        try {
            revisionRepository.actualizarRevision(id_revision, rol_autor, calificacion, comentario, fecha, id_servicio, id_usuario);
            return "Revision actualizada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar Revision: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_revision}")
    public String eliminarRevision(@PathVariable Integer id_revision) {
        try {
            revisionRepository.eliminarRevision(id_revision);
            return "Revision eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar Revision: " + e.getMessage();
        }
    }

    @GetMapping("/darRevisiones")
    public List<Revision> darRevisiones() {
        try {
            return revisionRepository.darRevisiones();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darRevision/{id_revision}")
    public Revision darRevision(@PathVariable Integer id_revision) {
        try {
            return revisionRepository.darRevision(id_revision);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
