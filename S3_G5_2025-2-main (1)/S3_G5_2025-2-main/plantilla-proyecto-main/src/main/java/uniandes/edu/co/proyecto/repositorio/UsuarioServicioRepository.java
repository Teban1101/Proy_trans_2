package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.UsuarioServicio;

public interface UsuarioServicioRepository extends JpaRepository<UsuarioServicio, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USUARIO_SERVICIO (id_usuario) VALUES (:id_usuario)", nativeQuery = true)
    void insertarUsuarioServicio(@Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM USUARIO_SERVICIO WHERE id_usuario = :id_usuario", nativeQuery = true)
    void eliminarUsuarioServicio(@Param("id_usuario") Integer id_usuario);

    @Query(value = "SELECT * FROM USUARIO_SERVICIO", nativeQuery = true)
    List<UsuarioServicio> darUsuariosServicio();

    @Query(value = "SELECT * FROM USUARIO_SERVICIO WHERE id_usuario = :id_usuario", nativeQuery = true)
    UsuarioServicio darUsuarioServicio(@Param("id_usuario") Integer id_usuario);
}
