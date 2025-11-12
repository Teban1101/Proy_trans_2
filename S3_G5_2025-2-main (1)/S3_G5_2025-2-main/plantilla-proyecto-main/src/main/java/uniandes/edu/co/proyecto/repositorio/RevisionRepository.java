package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Revision;

public interface RevisionRepository extends JpaRepository<Revision, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO REVISION (id_revision, rol_autor, calificacion, comentario, fecha, id_servicio, id_usuario) " +
                   "VALUES (:id_revision, :rol_autor, :calificacion, :comentario, :fecha, :id_servicio, :id_usuario)", nativeQuery = true)
    void insertarRevision(@Param("id_revision") Integer id_revision,
                          @Param("rol_autor") String rol_autor,
                          @Param("calificacion") Integer calificacion,
                          @Param("comentario") String comentario,
                          @Param("fecha") LocalDate fecha,
                          @Param("id_servicio") Integer id_servicio,
                          @Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE REVISION SET rol_autor = :rol_autor, calificacion = :calificacion, comentario = :comentario, fecha = :fecha, " +
                   "id_servicio = :id_servicio, id_usuario = :id_usuario WHERE id_revision = :id_revision", nativeQuery = true)
    void actualizarRevision(@Param("id_revision") Integer id_revision,
                            @Param("rol_autor") String rol_autor,
                            @Param("calificacion") Integer calificacion,
                            @Param("comentario") String comentario,
                            @Param("fecha") LocalDate fecha,
                            @Param("id_servicio") Integer id_servicio,
                            @Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM REVISION WHERE id_revision = :id_revision", nativeQuery = true)
    void eliminarRevision(@Param("id_revision") Integer id_revision);

    @Query(value = "SELECT * FROM REVISION", nativeQuery = true)
    List<Revision> darRevisiones();

    @Query(value = "SELECT * FROM REVISION WHERE id_revision = :id_revision", nativeQuery = true)
    Revision darRevision(@Param("id_revision") Integer id_revision);
}
