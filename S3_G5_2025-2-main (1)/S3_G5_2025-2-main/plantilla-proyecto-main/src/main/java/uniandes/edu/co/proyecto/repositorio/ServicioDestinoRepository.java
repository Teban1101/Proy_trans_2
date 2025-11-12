package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.ServicioDestino;
import uniandes.edu.co.proyecto.modelo.ServicioDestinoPK;

public interface ServicioDestinoRepository extends JpaRepository<ServicioDestino, ServicioDestinoPK> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO SERVICIO_DESTINO (id_servicio, id_punto, orden) " +
                   "VALUES (:id_servicio, :id_punto, :orden)", nativeQuery = true)
    void insertarServicioDestino(@Param("id_servicio") Integer id_servicio,
                                 @Param("id_punto") Integer id_punto,
                                 @Param("orden") Integer orden);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SERVICIO_DESTINO SET orden = :nuevo_orden " +
                   "WHERE id_servicio = :id_servicio AND id_punto = :id_punto AND orden = :orden", nativeQuery = true)
    void actualizarOrden(@Param("id_servicio") Integer id_servicio,
                         @Param("id_punto") Integer id_punto,
                         @Param("orden") Integer orden,
                         @Param("nuevo_orden") Integer nuevo_orden);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SERVICIO_DESTINO WHERE id_servicio = :id_servicio AND id_punto = :id_punto AND orden = :orden", nativeQuery = true)
    void eliminarServicioDestino(@Param("id_servicio") Integer id_servicio,
                                 @Param("id_punto") Integer id_punto,
                                 @Param("orden") Integer orden);

    @Query(value = "SELECT * FROM SERVICIO_DESTINO", nativeQuery = true)
    List<ServicioDestino> darServiciosDestino();

    @Query(value = "SELECT * FROM SERVICIO_DESTINO WHERE id_servicio = :id_servicio AND id_punto = :id_punto AND orden = :orden", nativeQuery = true)
    ServicioDestino darServicioDestino(@Param("id_servicio") Integer id_servicio,
                                       @Param("id_punto") Integer id_punto,
                                       @Param("orden") Integer orden);
}
