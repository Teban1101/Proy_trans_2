package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NIVEL_TRANSPORTE")
public class NivelTransporte {

    @Id
    private Integer id_nivel;

    private String nombre;

    public NivelTransporte() {}

    public NivelTransporte(Integer id_nivel, String nombre) {
        this.id_nivel = id_nivel;
        this.nombre = nombre;
    }

    public Integer getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(Integer id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
