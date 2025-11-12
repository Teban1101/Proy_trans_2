package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_VEHICULO")
public class TipoVehiculo {

    @Id
    private Integer id_tipo_vehiculo;

    private String nombre;

    public TipoVehiculo() {}

    public TipoVehiculo(Integer id_tipo_vehiculo, String nombre) {
        this.id_tipo_vehiculo = id_tipo_vehiculo;
        this.nombre = nombre;
    }

    public Integer getId_tipo_vehiculo() {
        return id_tipo_vehiculo;
    }

    public void setId_tipo_vehiculo(Integer id_tipo_vehiculo) {
        this.id_tipo_vehiculo = id_tipo_vehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
