package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "REVISION")
public class Revision {

    @Id
    private Integer id_revision;

    private String rol_autor;

    private Integer calificacion;

    private String comentario;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "id_servicio")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "cedula")
    private Usuario usuario;

    public Revision() {}

    public Revision(Integer id_revision, String rol_autor, Integer calificacion, String comentario,
                    LocalDate fecha, Servicio servicio, Usuario usuario) {
        this.id_revision = id_revision;
        this.rol_autor = rol_autor;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.servicio = servicio;
        this.usuario = usuario;
    }

    public Integer getId_revision() {
        return id_revision;
    }

    public void setId_revision(Integer id_revision) {
        this.id_revision = id_revision;
    }

    public String getRol_autor() {
        return rol_autor;
    }

    public void setRol_autor(String rol_autor) {
        this.rol_autor = rol_autor;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
