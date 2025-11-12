package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class ServicioDestinoPK implements Serializable {

    private Integer id_servicio;

    private Integer id_punto;

    private Integer orden;

    public ServicioDestinoPK() {}

    public ServicioDestinoPK(Integer id_servicio, Integer id_punto, Integer orden) {
        this.id_servicio = id_servicio;
        this.id_punto = id_punto;
        this.orden = orden;
    }

    public Integer getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(Integer id_servicio) {
        this.id_servicio = id_servicio;
    }

    public Integer getId_punto() {
        return id_punto;
    }

    public void setId_punto(Integer id_punto) {
        this.id_punto = id_punto;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        return ("" + id_servicio + "-" + id_punto + "-" + orden).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ServicioDestinoPK)) return false;
        ServicioDestinoPK other = (ServicioDestinoPK) obj;
        return this.id_servicio.equals(other.id_servicio)
            && this.id_punto.equals(other.id_punto)
            && this.orden.equals(other.orden);
    }
}
