package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TARJETA_CREDITO")
public class TarjetaCredito {

    @Id
    private String numero;

    private String nombre_tarjeta;

    private LocalDate vencimiento;

    private Integer cvv;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "cedula")
    private Usuario usuario;

    public TarjetaCredito() {}

    public TarjetaCredito(String numero, String nombre_tarjeta, LocalDate vencimiento, Integer cvv, Usuario usuario) {
        this.numero = numero;
        this.nombre_tarjeta = nombre_tarjeta;
        this.vencimiento = vencimiento;
        this.cvv = cvv;
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre_tarjeta() {
        return nombre_tarjeta;
    }

    public void setNombre_tarjeta(String nombre_tarjeta) {
        this.nombre_tarjeta = nombre_tarjeta;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
