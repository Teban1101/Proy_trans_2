package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "SERVICIO_DESTINO")
public class ServicioDestino {

    @EmbeddedId
    private ServicioDestinoPK id;

    @ManyToOne
    @MapsId("id_servicio")
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "id_servicio")
    private Servicio servicio;

    @ManyToOne
    @MapsId("id_punto")
    @JoinColumn(name = "ID_PUNTO", referencedColumnName = "id_punto")
    private PuntoGeografico punto;

    public ServicioDestino() {}

    public ServicioDestino(ServicioDestinoPK id, Servicio servicio, PuntoGeografico punto) {
        this.id = id;
        this.servicio = servicio;
        this.punto = punto;
    }

    public ServicioDestinoPK getId() {
        return id;
    }

    public void setId(ServicioDestinoPK id) {
        this.id = id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public PuntoGeografico getPunto() {
        return punto;
    }

    public void setPunto(PuntoGeografico punto) {
        this.punto = punto;
    }
}
