package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USUARIO (cedula, nombre, correo, celular) VALUES (:cedula, :nombre, :correo, :celular)", nativeQuery = true)
    void insertarUsuario(@Param("cedula") Integer cedula, @Param("nombre") String nombre,
                         @Param("correo") String correo, @Param("celular") String celular);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USUARIO SET nombre = :nombre, correo = :correo, celular = :celular WHERE cedula = :cedula", nativeQuery = true)
    void actualizarUsuario(@Param("cedula") Integer cedula, @Param("nombre") String nombre,
                           @Param("correo") String correo, @Param("celular") String celular);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM USUARIO WHERE cedula = :cedula", nativeQuery = true)
    void eliminarUsuario(@Param("cedula") Integer cedula);

    @Query(value = "SELECT * FROM USUARIO", nativeQuery = true)
    List<Usuario> darUsuarios();

    @Query(value = "SELECT * FROM USUARIO WHERE cedula = :cedula", nativeQuery = true)
    Usuario darUsuario(@Param("cedula") Integer cedula);
}
