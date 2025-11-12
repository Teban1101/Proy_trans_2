package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VEHICULO")
public class Vehiculo {

    @Id
    private String placa;

    private String marca;

    private String modelo;

    private String color;

    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "cedula")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_VEHICULO", referencedColumnName = "id_tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;

    @ManyToOne
    @JoinColumn(name = "ID_NIVEL", referencedColumnName = "id_nivel")
    private NivelTransporte nivel;

    @ManyToOne
    @JoinColumn(name = "ID_CIUDAD", referencedColumnName = "id_ciudad")
    private Ciudad ciudad;

    public Vehiculo() {}

    public Vehiculo(String placa, String marca, String modelo, String color, Integer capacidad,
                    Usuario usuario, TipoVehiculo tipoVehiculo, NivelTransporte nivel, Ciudad ciudad) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.capacidad = capacidad;
        this.usuario = usuario;
        this.tipoVehiculo = tipoVehiculo;
        this.nivel = nivel;
        this.ciudad = ciudad;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public NivelTransporte getNivel() {
        return nivel;
    }

    public void setNivel(NivelTransporte nivel) {
        this.nivel = nivel;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
