package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_SERVICIO")
public class TipoServicio {

    @Id
    private Integer id_tipo_servicio;

    private String nombre;

    public TipoServicio() {}

    public TipoServicio(Integer id_tipo_servicio, String nombre) {
        this.id_tipo_servicio = id_tipo_servicio;
        this.nombre = nombre;
    }

    public Integer getId_tipo_servicio() {
        return id_tipo_servicio;
    }

    public void setId_tipo_servicio(Integer id_tipo_servicio) {
        this.id_tipo_servicio = id_tipo_servicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
