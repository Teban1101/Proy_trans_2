package uniandes.edu.co.proyecto.repositorio;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Disponibilidad;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo) " +
                   "VALUES (:id_disponibilidad, :dia_semana, :hora_inicio, :hora_fin, :id_usuario, :id_vehiculo)", nativeQuery = true)
    void insertarDisponibilidad(@Param("id_disponibilidad") Integer id_disponibilidad, @Param("dia_semana") Integer dia_semana,
                                @Param("hora_inicio") OffsetDateTime hora_inicio, @Param("hora_fin") OffsetDateTime hora_fin,
                                @Param("id_usuario") Integer id_usuario, @Param("id_vehiculo") String id_vehiculo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE DISPONIBILIDAD SET dia_semana = :dia_semana, hora_inicio = :hora_inicio, hora_fin = :hora_fin, " +
                   "id_usuario = :id_usuario, id_vehiculo = :id_vehiculo WHERE id_disponibilidad = :id_disponibilidad", nativeQuery = true)
    void actualizarDisponibilidad(@Param("id_disponibilidad") Integer id_disponibilidad, @Param("dia_semana") Integer dia_semana,
                                  @Param("hora_inicio") OffsetDateTime hora_inicio, @Param("hora_fin") OffsetDateTime hora_fin,
                                  @Param("id_usuario") Integer id_usuario, @Param("id_vehiculo") String id_vehiculo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM DISPONIBILIDAD WHERE id_disponibilidad = :id_disponibilidad", nativeQuery = true)
    void eliminarDisponibilidad(@Param("id_disponibilidad") Integer id_disponibilidad);

    @Query(value = "SELECT * FROM DISPONIBILIDAD", nativeQuery = true)
    List<Disponibilidad> darDisponibilidades();

    @Query(value = "SELECT * FROM DISPONIBILIDAD WHERE id_disponibilidad = :id_disponibilidad", nativeQuery = true)
    Disponibilidad darDisponibilidad(@Param("id_disponibilidad") Integer id_disponibilidad);

    /* ====== Validación RF5/RF6: superposición de horarios ====== */
    @Query(value = """
        SELECT * FROM DISPONIBILIDAD
        WHERE id_usuario = :id_usuario
          AND id_vehiculo = :id_vehiculo
          AND dia_semana = :dia_semana
          AND NOT (hora_fin <= :hora_inicio OR hora_inicio >= :hora_fin)
        """, nativeQuery = true)
    List<Disponibilidad> verificarSuperposicion(@Param("id_usuario") Integer id_usuario,
                                                @Param("id_vehiculo") String id_vehiculo,
                                                @Param("dia_semana") Integer dia_semana,
                                                @Param("hora_inicio") OffsetDateTime hora_inicio,
                                                @Param("hora_fin") OffsetDateTime hora_fin);

    /* ====== Soporte RF8: candidatos disponibles ahora mismo ======
       Retorna pares (id_usuario, id_vehiculo) con disponibilidad que cubre 'ahora'
       y sin servicio activo (hora_fin IS NULL) para ese par.
    */
    @Query(value = """
        SELECT d.id_usuario, d.id_vehiculo
        FROM DISPONIBILIDAD d
        WHERE d.dia_semana = :dia_semana
          AND d.hora_inicio <= :ahora
          AND d.hora_fin >  :ahora
          AND NOT EXISTS (
            SELECT 1 FROM SERVICIO s
            WHERE s.id_conductor = d.id_usuario
              AND s.id_vehiculo  = d.id_vehiculo
              AND s.hora_fin IS NULL
          )
        """, nativeQuery = true)
    List<Object[]> buscarDisponiblesEnMomento(@Param("dia_semana") Integer dia_semana,
                                              @Param("ahora") OffsetDateTime ahora);
}
