package uniandes.edu.co.proyecto.modelo;

import java.time.OffsetDateTime;

public class ServicioDTO {
    private Integer id_servicio;
    private Integer id_usuario_servicio;
    private Integer id_conductor;
    private String id_vehiculo;
    private Double distancia_km;
    private Long costo_total;
    private OffsetDateTime hora_inicio;
    private OffsetDateTime hora_fin;

    public ServicioDTO(Integer id_servicio, Integer id_usuario_servicio, Integer id_conductor,
                       String id_vehiculo, Double distancia_km, Long costo_total,
                       OffsetDateTime hora_inicio, OffsetDateTime hora_fin) {
        this.id_servicio = id_servicio;
        this.id_usuario_servicio = id_usuario_servicio;
        this.id_conductor = id_conductor;
        this.id_vehiculo = id_vehiculo;
        this.distancia_km = distancia_km;
        this.costo_total = costo_total;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    public Integer getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(Integer id_servicio) {
        this.id_servicio = id_servicio;
    }

    public Integer getId_usuario_servicio() {
        return id_usuario_servicio;
    }

    public void setId_usuario_servicio(Integer id_usuario_servicio) {
        this.id_usuario_servicio = id_usuario_servicio;
    }

    public Integer getId_conductor() {
        return id_conductor;
    }

    public void setId_conductor(Integer id_conductor) {
        this.id_conductor = id_conductor;
    }

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public Double getDistancia_km() {
        return distancia_km;
    }

    public void setDistancia_km(Double distancia_km) {
        this.distancia_km = distancia_km;
    }

    public Long getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(Long costo_total) {
        this.costo_total = costo_total;
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
}
