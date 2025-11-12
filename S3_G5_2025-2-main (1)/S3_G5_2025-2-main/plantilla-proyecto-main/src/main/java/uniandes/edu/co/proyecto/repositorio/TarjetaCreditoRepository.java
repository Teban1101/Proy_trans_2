package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.TarjetaCredito;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO TARJETA_CREDITO (numero, nombre_tarjeta, vencimiento, cvv, id_usuario) " +
                   "VALUES (:numero, :nombre_tarjeta, :vencimiento, :cvv, :id_usuario)", nativeQuery = true)
    void insertarTarjeta(@Param("numero") String numero,
                         @Param("nombre_tarjeta") String nombre_tarjeta,
                         @Param("vencimiento") LocalDate vencimiento,
                         @Param("cvv") Integer cvv,
                         @Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE TARJETA_CREDITO SET nombre_tarjeta = :nombre_tarjeta, vencimiento = :vencimiento, cvv = :cvv, " +
                   "id_usuario = :id_usuario WHERE numero = :numero", nativeQuery = true)
    void actualizarTarjeta(@Param("numero") String numero,
                           @Param("nombre_tarjeta") String nombre_tarjeta,
                           @Param("vencimiento") LocalDate vencimiento,
                           @Param("cvv") Integer cvv,
                           @Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TARJETA_CREDITO WHERE numero = :numero", nativeQuery = true)
    void eliminarTarjeta(@Param("numero") String numero);

    @Query(value = "SELECT * FROM TARJETA_CREDITO", nativeQuery = true)
    List<TarjetaCredito> darTarjetas();

    @Query(value = "SELECT * FROM TARJETA_CREDITO WHERE numero = :numero", nativeQuery = true)
    TarjetaCredito darTarjeta(@Param("numero") String numero);

    @Query(value = "SELECT COUNT(*) FROM TARJETA_CREDITO WHERE id_usuario = :id_usuario", nativeQuery = true)
    Integer contarTarjetasPorUsuario(@Param("id_usuario") Integer id_usuario);
}
