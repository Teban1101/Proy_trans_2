package uniandes.edu.co.proyecto.repositorio;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    /* ===================== CRUD ===================== */

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO SERVICIO (id_servicio, distancia_km, costo_total, hora_inicio, hora_fin, " +
                   "id_tipo_servicio, id_usuario_servicio, id_conductor, id_vehiculo) " +
                   "VALUES (:id_servicio, :distancia_km, :costo_total, :hora_inicio, :hora_fin, " +
                   ":id_tipo_servicio, :id_usuario_servicio, :id_conductor, :id_vehiculo)", nativeQuery = true)
    void insertarServicio(@Param("id_servicio") Integer id_servicio,
                          @Param("distancia_km") Double distancia_km,
                          @Param("costo_total") Long costo_total,
                          @Param("hora_inicio") OffsetDateTime hora_inicio,
                          @Param("hora_fin") OffsetDateTime hora_fin,
                          @Param("id_tipo_servicio") Integer id_tipo_servicio,
                          @Param("id_usuario_servicio") Integer id_usuario_servicio,
                          @Param("id_conductor") Integer id_conductor,
                          @Param("id_vehiculo") String id_vehiculo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SERVICIO SET distancia_km = :distancia_km, costo_total = :costo_total, " +
                   "hora_inicio = :hora_inicio, hora_fin = :hora_fin, id_tipo_servicio = :id_tipo_servicio, " +
                   "id_usuario_servicio = :id_usuario_servicio, id_conductor = :id_conductor, id_vehiculo = :id_vehiculo " +
                   "WHERE id_servicio = :id_servicio", nativeQuery = true)
    void actualizarServicio(@Param("id_servicio") Integer id_servicio,
                            @Param("distancia_km") Double distancia_km,
                            @Param("costo_total") Long costo_total,
                            @Param("hora_inicio") OffsetDateTime hora_inicio,
                            @Param("hora_fin") OffsetDateTime hora_fin,
                            @Param("id_tipo_servicio") Integer id_tipo_servicio,
                            @Param("id_usuario_servicio") Integer id_usuario_servicio,
                            @Param("id_conductor") Integer id_conductor,
                            @Param("id_vehiculo") String id_vehiculo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SERVICIO WHERE id_servicio = :id_servicio", nativeQuery = true)
    void eliminarServicio(@Param("id_servicio") Integer id_servicio);

    @Query(value = "SELECT * FROM SERVICIO", nativeQuery = true)
    List<Servicio> darServicios();

    @Query(value = "SELECT * FROM SERVICIO WHERE id_servicio = :id_servicio", nativeQuery = true)
    Servicio darServicio(@Param("id_servicio") Integer id_servicio);

    /* ====================== RFCs ====================== */

    @Query(
      value = """
      SELECT S.ID_SERVICIO,
             S.HORA_INICIO,
             S.HORA_FIN,
             S.DISTANCIA_KM,
             S.COSTO_TOTAL,
             TS.NOMBRE          AS TIPO_SERVICIO,
             U_CLI.NOMBRE       AS NOMBRE_CLIENTE,
             U_CON.CEDULA       AS ID_CONDUCTOR,
             U_CON.NOMBRE       AS NOMBRE_CONDUCTOR,
             V.PLACA            AS PLACA_VEHICULO,
             TV.NOMBRE          AS TIPO_VEHICULO,
             NT.NOMBRE          AS NIVEL_TRANSPORTE,
             C.NOMBRE           AS CIUDAD_VEHICULO
      FROM   SERVICIO S
      JOIN   TIPO_SERVICIO     TS  ON TS.ID_TIPO_SERVICIO = S.ID_TIPO_SERVICIO
      JOIN   USUARIO           U_CLI ON U_CLI.CEDULA = S.ID_USUARIO_SERVICIO
      LEFT  JOIN USUARIO        U_CON ON U_CON.CEDULA = S.ID_CONDUCTOR
      LEFT  JOIN VEHICULO       V    ON V.PLACA = S.ID_VEHICULO
      LEFT  JOIN TIPO_VEHICULO  TV   ON TV.ID_TIPO_VEHICULO = V.ID_TIPO_VEHICULO
      LEFT  JOIN NIVEL_TRANSPORTE NT ON NT.ID_NIVEL = V.ID_NIVEL
      LEFT  JOIN CIUDAD         C    ON C.ID_CIUDAD = V.ID_CIUDAD
      WHERE  S.ID_USUARIO_SERVICIO = :P_USUARIO
      ORDER BY S.HORA_INICIO DESC
      """,
      nativeQuery = true
    )
    List<Object[]> rfc1HistoricoPorUsuario(@Param("P_USUARIO") Integer idUsuario);

    @Query(
      value = """
      SELECT U.CEDULA        AS ID_CONDUCTOR,
             U.NOMBRE        AS NOMBRE_CONDUCTOR,
             COUNT(*)        AS TOTAL_SERVICIOS
      FROM   SERVICIO S
      JOIN   USUARIO  U ON U.CEDULA = S.ID_CONDUCTOR
      GROUP BY U.CEDULA, U.NOMBRE
      ORDER BY TOTAL_SERVICIOS DESC
      FETCH FIRST 20 ROWS ONLY
      """,
      nativeQuery = true
    )
    List<Object[]> rfc2Top20Conductores();

    @Query(
      value = """
      SELECT U.CEDULA                                        AS ID_CONDUCTOR,
             U.NOMBRE                                        AS NOMBRE_CONDUCTOR,
             V.PLACA                                         AS PLACA_VEHICULO,
             S.ID_SERVICIO                                   AS ID_SERVICIO,
             S.COSTO_TOTAL                                   AS COSTO_TOTAL,
             ROUND(S.COSTO_TOTAL * (1 - (:P_COMISION_PCT/100)), 2) AS NETO_CONDUCTOR
      FROM   SERVICIO S
      JOIN   USUARIO  U ON U.CEDULA = S.ID_CONDUCTOR
      JOIN   VEHICULO V ON V.PLACA  = S.ID_VEHICULO
      ORDER BY U.CEDULA, V.PLACA, S.ID_SERVICIO
      """,
      nativeQuery = true
    )
    List<Object[]> rfc3DetallePorServicio(@Param("P_COMISION_PCT") Double comisionPct);

    @Query(
      value = """
      SELECT U.CEDULA  AS ID_CONDUCTOR,
             U.NOMBRE  AS NOMBRE_CONDUCTOR,
             V.PLACA   AS PLACA_VEHICULO,
             COUNT(*)  AS NUM_SERVICIOS,
             ROUND(SUM(S.COSTO_TOTAL) * (1 - (:P_COMISION_PCT/100)), 2) AS TOTAL_NETO
      FROM   SERVICIO S
      JOIN   USUARIO  U ON U.CEDULA = S.ID_CONDUCTOR
      JOIN   VEHICULO V ON V.PLACA  = S.ID_VEHICULO
      GROUP BY U.CEDULA, U.NOMBRE, V.PLACA
      ORDER BY U.CEDULA, V.PLACA
      """,
      nativeQuery = true
    )
    List<Object[]> rfc3AgregadoPorVehiculo(@Param("P_COMISION_PCT") Double comisionPct);

    @Query(
      value = """
      SELECT TS.NOMBRE                         AS TIPO_SERVICIO,
             NVL(NT.NOMBRE,'SIN NIVEL')        AS NIVEL_TRANSPORTE,
             COUNT(*)                          AS TOTAL_SERVICIOS,
             ROUND(100 * RATIO_TO_REPORT(COUNT(*)) OVER (), 2) AS PORCENTAJE_TOTAL
      FROM   SERVICIO S
      JOIN   VEHICULO V       ON V.PLACA     = S.ID_VEHICULO
      JOIN   CIUDAD   C       ON C.ID_CIUDAD = V.ID_CIUDAD
      JOIN   TIPO_SERVICIO TS ON TS.ID_TIPO_SERVICIO = S.ID_TIPO_SERVICIO
      LEFT  JOIN NIVEL_TRANSPORTE NT ON NT.ID_NIVEL = V.ID_NIVEL
      WHERE  UPPER(C.NOMBRE) = UPPER(:P_CIUDAD)
        AND  S.HORA_INICIO >= :P_INI
        AND  S.HORA_INICIO <  :P_FIN
      GROUP BY TS.NOMBRE, NT.NOMBRE
      ORDER BY TOTAL_SERVICIOS DESC
      """,
      nativeQuery = true
    )
    List<Object[]> rfc4UsoPorCiudadYRango(@Param("P_CIUDAD") String ciudad,
                                          @Param("P_INI") OffsetDateTime fechaInicio,
                                          @Param("P_FIN") OffsetDateTime fechaFin);

    /* =================== RF8 / RF9 =================== */

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO SERVICIO (id_servicio, distancia_km, costo_total, hora_inicio,
                              id_tipo_servicio, id_usuario_servicio, id_conductor, id_vehiculo)
        VALUES (:id_servicio, :distancia_km, :costo_total, :hora_inicio,
                :id_tipo_servicio, :id_usuario_servicio, :id_conductor, :id_vehiculo)
        """, nativeQuery = true)
    void solicitarServicio(@Param("id_servicio") Integer id_servicio,
                           @Param("distancia_km") Double distancia_km,
                           @Param("costo_total") Long costo_total,
                           @Param("hora_inicio") OffsetDateTime hora_inicio,
                           @Param("id_tipo_servicio") Integer id_tipo_servicio,
                           @Param("id_usuario_servicio") Integer id_usuario_servicio,
                           @Param("id_conductor") Integer id_conductor,
                           @Param("id_vehiculo") String id_vehiculo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SERVICIO SET hora_fin = :hora_fin WHERE id_servicio = :id_servicio", nativeQuery = true)
    void terminarServicio(@Param("id_servicio") Integer id_servicio,
                          @Param("hora_fin") OffsetDateTime hora_fin);

    @Query(value = "SELECT id_servicio, id_usuario_servicio, id_conductor, id_vehiculo, " +
                   "distancia_km, costo_total, hora_inicio, hora_fin FROM SERVICIO", nativeQuery = true)
    List<Map<String, Object>> listarServiciosSimple();

    /**
     * RFC1 - Obtener historial de servicios por usuario
     * Utilizado por pruebas de concurrencia
     */
    @Query(value = "SELECT s FROM Servicio s WHERE s.id_usuario_servicio = :id_usuario ORDER BY s.hora_inicio DESC")
    List<Servicio> findByIdUsuarioServicio(@Param("id_usuario") Integer id_usuario);
}
