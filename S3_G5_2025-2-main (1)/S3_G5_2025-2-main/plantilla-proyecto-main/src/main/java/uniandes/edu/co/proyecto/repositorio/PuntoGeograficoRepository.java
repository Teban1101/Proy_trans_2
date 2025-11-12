package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.PuntoGeografico;

public interface PuntoGeograficoRepository extends JpaRepository<PuntoGeografico, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO PUNTO_GEOGRAFICO (id_punto, nombre, direccion, latitud, longitud, id_ciudad) " +
                   "VALUES (:id_punto, :nombre, :direccion, :latitud, :longitud, :id_ciudad)", nativeQuery = true)
    void insertarPunto(@Param("id_punto") Integer id_punto, @Param("nombre") String nombre,
                       @Param("direccion") String direccion, @Param("latitud") Double latitud,
                       @Param("longitud") Double longitud, @Param("id_ciudad") Integer id_ciudad);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PUNTO_GEOGRAFICO SET nombre = :nombre, direccion = :direccion, " +
                   "latitud = :latitud, longitud = :longitud, id_ciudad = :id_ciudad WHERE id_punto = :id_punto", nativeQuery = true)
    void actualizarPunto(@Param("id_punto") Integer id_punto, @Param("nombre") String nombre,
                         @Param("direccion") String direccion, @Param("latitud") Double latitud,
                         @Param("longitud") Double longitud, @Param("id_ciudad") Integer id_ciudad);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM PUNTO_GEOGRAFICO WHERE id_punto = :id_punto", nativeQuery = true)
    void eliminarPunto(@Param("id_punto") Integer id_punto);

    // Prefer JPQL so JPA handles mapping between columns and entity properties
    @Query("SELECT p FROM PuntoGeografico p")
    List<PuntoGeografico> darPuntos();

    @Query("SELECT p FROM PuntoGeografico p WHERE p.id_punto = :id_punto")
    PuntoGeografico darPunto(@Param("id_punto") Integer id_punto);
}
