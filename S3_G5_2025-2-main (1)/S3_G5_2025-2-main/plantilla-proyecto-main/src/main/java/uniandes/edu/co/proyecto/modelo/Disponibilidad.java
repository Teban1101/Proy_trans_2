package uniandes.edu.co.proyecto.modelo;

import java.time.OffsetDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DISPONIBILIDAD")
public class Disponibilidad {

    @Id
    private Integer id_disponibilidad;

    private Integer dia_semana;

    private OffsetDateTime hora_inicio;

    private OffsetDateTime hora_fin;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "cedula")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO", referencedColumnName = "placa")
    private Vehiculo vehiculo;

    public Disponibilidad() {}

    public Disponibilidad(Integer id_disponibilidad, Integer dia_semana, OffsetDateTime hora_inicio,
                          OffsetDateTime hora_fin, Usuario usuario, Vehiculo vehiculo) {
        this.id_disponibilidad = id_disponibilidad;
        this.dia_semana = dia_semana;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.usuario = usuario;
        this.vehiculo = vehiculo;
    }

    public Integer getId_disponibilidad() {
        return id_disponibilidad;
    }

    public void setId_disponibilidad(Integer id_disponibilidad) {
        this.id_disponibilidad = id_disponibilidad;
    }

    public Integer getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(Integer dia_semana) {
        this.dia_semana = dia_semana;
    }

    public OffsetDateTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(OffsetDateTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public OffsetDateTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(OffsetDateTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
