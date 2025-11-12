package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.TipoServicio;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO TIPO_SERVICIO (id_tipo_servicio, nombre) VALUES (:id, :nombre)", nativeQuery = true)
    void insertarTipoServicio(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "UPDATE TIPO_SERVICIO SET nombre = :nombre WHERE id_tipo_servicio = :id", nativeQuery = true)
    void actualizarTipoServicio(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TIPO_SERVICIO WHERE id_tipo_servicio = :id", nativeQuery = true)
    void eliminarTipoServicio(@Param("id") Integer id);

    @Query(value = "SELECT * FROM TIPO_SERVICIO", nativeQuery = true)
    List<TipoServicio> darTiposServicio();

    @Query(value = "SELECT * FROM TIPO_SERVICIO WHERE id_tipo_servicio = :id", nativeQuery = true)
    TipoServicio darTipoServicio(@Param("id") Integer id);
}
