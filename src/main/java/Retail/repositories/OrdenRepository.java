package Retail.repositories;

import Retail.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden,Integer> {

    @Query("SELECT o FROM Orden o WHERE o.cliente.id = :idCliente")
    List<Orden> findByCliente(@Param("idCliente") Integer idCliente);
}
