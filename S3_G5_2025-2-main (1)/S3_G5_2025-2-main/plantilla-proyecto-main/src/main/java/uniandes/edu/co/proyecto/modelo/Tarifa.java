package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TARIFA")
public class Tarifa {

    @Id
    private Integer id_tarifa;

    private Double valor_por_km;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_SERVICIO", referencedColumnName = "id_tipo_servicio")
    private TipoServicio tipoServicio;

    @ManyToOne
    @JoinColumn(name = "ID_NIVEL", referencedColumnName = "id_nivel")
    private NivelTransporte nivel;

    public Tarifa() {}

    public Tarifa(Integer id_tarifa, Double valor_por_km, TipoServicio tipoServicio, NivelTransporte nivel) {
        this.id_tarifa = id_tarifa;
        this.valor_por_km = valor_por_km;
        this.tipoServicio = tipoServicio;
        this.nivel = nivel;
    }

    public Integer getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(Integer id_tarifa) {
        this.id_tarifa = id_tarifa;
    }

    public Double getValor_por_km() {
        return valor_por_km;
    }

    public void setValor_por_km(Double valor_por_km) {
        this.valor_por_km = valor_por_km;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public NivelTransporte getNivel() {
        return nivel;
    }

    public void setNivel(NivelTransporte nivel) {
        this.nivel = nivel;
    }
}
