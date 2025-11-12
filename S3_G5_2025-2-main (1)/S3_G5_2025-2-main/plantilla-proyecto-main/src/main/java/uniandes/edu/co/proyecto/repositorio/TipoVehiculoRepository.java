package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.TipoVehiculo;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO TIPO_VEHICULO (id_tipo_vehiculo, nombre) VALUES (:id, :nombre)", nativeQuery = true)
    void insertarTipoVehiculo(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "UPDATE TIPO_VEHICULO SET nombre = :nombre WHERE id_tipo_vehiculo = :id", nativeQuery = true)
    void actualizarTipoVehiculo(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TIPO_VEHICULO WHERE id_tipo_vehiculo = :id", nativeQuery = true)
    void eliminarTipoVehiculo(@Param("id") Integer id);

    @Query(value = "SELECT * FROM TIPO_VEHICULO", nativeQuery = true)
    List<TipoVehiculo> darTiposVehiculo();

    @Query(value = "SELECT * FROM TIPO_VEHICULO WHERE id_tipo_vehiculo = :id", nativeQuery = true)
    TipoVehiculo darTipoVehiculo(@Param("id") Integer id);
}
