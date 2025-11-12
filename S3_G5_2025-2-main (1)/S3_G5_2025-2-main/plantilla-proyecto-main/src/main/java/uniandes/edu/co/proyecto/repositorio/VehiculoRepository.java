package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO VEHICULO (placa, marca, modelo, color, capacidad, id_usuario, id_tipo_vehiculo, id_nivel, id_ciudad) " +
                   "VALUES (:placa, :marca, :modelo, :color, :capacidad, :id_usuario, :id_tipo_vehiculo, :id_nivel, :id_ciudad)", nativeQuery = true)
    void insertarVehiculo(@Param("placa") String placa, @Param("marca") String marca, @Param("modelo") String modelo,
                          @Param("color") String color, @Param("capacidad") Integer capacidad,
                          @Param("id_usuario") Integer id_usuario, @Param("id_tipo_vehiculo") Integer id_tipo_vehiculo,
                          @Param("id_nivel") Integer id_nivel, @Param("id_ciudad") Integer id_ciudad);

    @Modifying
    @Transactional
    @Query(value = "UPDATE VEHICULO SET marca = :marca, modelo = :modelo, color = :color, capacidad = :capacidad, " +
                   "id_usuario = :id_usuario, id_tipo_vehiculo = :id_tipo_vehiculo, id_nivel = :id_nivel, id_ciudad = :id_ciudad " +
                   "WHERE placa = :placa", nativeQuery = true)
    void actualizarVehiculo(@Param("placa") String placa, @Param("marca") String marca, @Param("modelo") String modelo,
                            @Param("color") String color, @Param("capacidad") Integer capacidad,
                            @Param("id_usuario") Integer id_usuario, @Param("id_tipo_vehiculo") Integer id_tipo_vehiculo,
                            @Param("id_nivel") Integer id_nivel, @Param("id_ciudad") Integer id_ciudad);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM VEHICULO WHERE placa = :placa", nativeQuery = true)
    void eliminarVehiculo(@Param("placa") String placa);

    @Query(value = "SELECT * FROM VEHICULO", nativeQuery = true)
    List<Vehiculo> darVehiculos();

    @Query(value = "SELECT * FROM VEHICULO WHERE placa = :placa", nativeQuery = true)
    Vehiculo darVehiculo(@Param("placa") String placa);

    @Query(value = "SELECT id_nivel FROM VEHICULO WHERE placa = :placa", nativeQuery = true)
    Integer obtenerIdNivel(@Param("placa") String placa);

    @Query(value = "SELECT * FROM VEHICULO WHERE id_usuario = :id_usuario", nativeQuery = true)
    List<Vehiculo> darVehiculosPorUsuario(@Param("id_usuario") Integer id_usuario);

    @Query(value = "SELECT placa FROM VEHICULO WHERE id_usuario = :id_usuario", nativeQuery = true)
    String obtenerVehiculoPorConductor(@Param("id_usuario") Integer id_usuario);

}
