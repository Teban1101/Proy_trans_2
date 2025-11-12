package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "PUNTO_GEOGRAFICO")
public class PuntoGeografico {

    @Id
    private Integer id_punto;

    private String nombre;

    private String direccion;

    private Double latitud;

    private Double longitud;

    @Column(name = "ID_CIUDAD")
    private Integer id_ciudad;

    // Nota: la tabla PUNTO_GEOGRAFICO del esquema no contiene una columna de FK a CIUDAD
    // con el mismo nombre esperado por JPA, así que dejamos solo el id_ciudad como entero
    // y no modelamos la relación ManyToOne para evitar que JPA genere columnas inválidas.

    public PuntoGeografico() {}

    public PuntoGeografico(Integer id_punto, String nombre, String direccion, Double latitud, Double longitud, Integer id_ciudad) {
        this.id_punto = id_punto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id_ciudad = id_ciudad;
    }

    public Integer getId_punto() {
        return id_punto;
    }

    public void setId_punto(Integer id_punto) {
        this.id_punto = id_punto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Integer getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(Integer id_ciudad) {
        this.id_ciudad = id_ciudad;
    }
}
