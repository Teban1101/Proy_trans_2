package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Tarifa;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO TARIFA (id_tarifa, valor_por_km, id_tipo_servicio, id_nivel) " +
                   "VALUES (:id_tarifa, :valor_por_km, :id_tipo_servicio, :id_nivel)", nativeQuery = true)
    void insertarTarifa(@Param("id_tarifa") Integer id_tarifa,
                        @Param("valor_por_km") Double valor_por_km,
                        @Param("id_tipo_servicio") Integer id_tipo_servicio,
                        @Param("id_nivel") Integer id_nivel);

    @Modifying
    @Transactional
    @Query(value = "UPDATE TARIFA SET valor_por_km = :valor_por_km, id_tipo_servicio = :id_tipo_servicio, id_nivel = :id_nivel " +
                   "WHERE id_tarifa = :id_tarifa", nativeQuery = true)
    void actualizarTarifa(@Param("id_tarifa") Integer id_tarifa,
                          @Param("valor_por_km") Double valor_por_km,
                          @Param("id_tipo_servicio") Integer id_tipo_servicio,
                          @Param("id_nivel") Integer id_nivel);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TARIFA WHERE id_tarifa = :id_tarifa", nativeQuery = true)
    void eliminarTarifa(@Param("id_tarifa") Integer id_tarifa);

    @Query(value = "SELECT * FROM TARIFA", nativeQuery = true)
    List<Tarifa> darTarifas();

    @Query(value = "SELECT * FROM TARIFA WHERE id_tarifa = :id_tarifa", nativeQuery = true)
    Tarifa darTarifa(@Param("id_tarifa") Integer id_tarifa);

    /* ====== Soporte RF8: obtener valor por km por tipo y nivel (nivel puede ser NULL) ====== */
    @Query(value = """
        SELECT valor_por_km
        FROM TARIFA
        WHERE id_tipo_servicio = :id_tipo_servicio
          AND (
                (id_nivel = :id_nivel)
                OR (id_nivel IS NULL AND :id_nivel IS NULL)
              )
        """, nativeQuery = true)
    Double obtenerValorPorKm(@Param("id_tipo_servicio") Integer id_tipo_servicio,
                             @Param("id_nivel") Integer id_nivel);
}
