package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.UsuarioConductor;

public interface UsuarioConductorRepository extends JpaRepository<UsuarioConductor, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USUARIO_CONDUCTOR (id_usuario) VALUES (:id_usuario)", nativeQuery = true)
    void insertarUsuarioConductor(@Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM USUARIO_CONDUCTOR WHERE id_usuario = :id_usuario", nativeQuery = true)
    void eliminarUsuarioConductor(@Param("id_usuario") Integer id_usuario);

    @Query(value = "SELECT * FROM USUARIO_CONDUCTOR", nativeQuery = true)
    List<UsuarioConductor> darUsuariosConductor();

    @Query(value = "SELECT * FROM USUARIO_CONDUCTOR WHERE id_usuario = :id_usuario", nativeQuery = true)
    UsuarioConductor darUsuarioConductor(@Param("id_usuario") Integer id_usuario);
}
