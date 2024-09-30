package br.com.fiap.lanchonete.infrastructure.database.repositories;

import br.com.fiap.lanchonete.core.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, String> {

    @Query("SELECT p FROM PedidoEntity p join fetch p.produtos prod order by p.dataHora")
    List<PedidoEntity> findAllComProdutos();

}
