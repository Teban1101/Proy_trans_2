package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.NivelTransporte;

public interface NivelTransporteRepository extends JpaRepository<NivelTransporte, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO NIVEL_TRANSPORTE (id_nivel, nombre) VALUES (:id, :nombre)", nativeQuery = true)
    void insertarNivelTransporte(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "UPDATE NIVEL_TRANSPORTE SET nombre = :nombre WHERE id_nivel = :id", nativeQuery = true)
    void actualizarNivelTransporte(@Param("id") Integer id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM NIVEL_TRANSPORTE WHERE id_nivel = :id", nativeQuery = true)
    void eliminarNivelTransporte(@Param("id") Integer id);

    @Query(value = "SELECT * FROM NIVEL_TRANSPORTE", nativeQuery = true)
    List<NivelTransporte> darNivelesTransporte();

    @Query(value = "SELECT * FROM NIVEL_TRANSPORTE WHERE id_nivel = :id", nativeQuery = true)
    NivelTransporte darNivelTransporte(@Param("id") Integer id);
}
