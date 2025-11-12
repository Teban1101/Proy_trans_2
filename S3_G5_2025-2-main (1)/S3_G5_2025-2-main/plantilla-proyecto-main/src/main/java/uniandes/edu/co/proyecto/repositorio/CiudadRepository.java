package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    /* Insertar ciudad */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO CIUDAD (id_ciudad, nombre) VALUES (:id_ciudad, :nombre)", nativeQuery = true)
    void insertarCiudad(@Param("id_ciudad") Integer id_ciudad, @Param("nombre") String nombre);

    /* Actualizar ciudad */
    @Modifying
    @Transactional
    @Query(value = "UPDATE CIUDAD SET nombre = :nombre WHERE id_ciudad = :id_ciudad", nativeQuery = true)
    void actualizarCiudad(@Param("id_ciudad") Integer id_ciudad, @Param("nombre") String nombre);

    /* Eliminar ciudad */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CIUDAD WHERE id_ciudad = :id_ciudad", nativeQuery = true)
    void eliminarCiudad(@Param("id_ciudad") Integer id_ciudad);

    /* Consultar todas las ciudades */
    @Query(value = "SELECT * FROM CIUDAD", nativeQuery = true)
    List<Ciudad> darCiudades();

    /* Consultar ciudad por id */
    @Query(value = "SELECT * FROM CIUDAD WHERE id_ciudad = :id_ciudad", nativeQuery = true)
    Ciudad darCiudad(@Param("id_ciudad") Integer id_ciudad);
}
