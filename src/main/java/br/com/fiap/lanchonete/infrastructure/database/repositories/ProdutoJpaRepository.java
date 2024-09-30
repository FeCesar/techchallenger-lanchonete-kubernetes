package br.com.fiap.lanchonete.infrastructure.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.lanchonete.core.entities.CategoriaEntity;
import br.com.fiap.lanchonete.core.entities.ProdutoEntity;
import jakarta.transaction.Transactional;

public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, String> {
    @Query("SELECT p FROM ProdutoEntity p WHERE p.ativo = true and p.id = :idProduto")
    Optional<ProdutoEntity> findByIdProduto(String idProduto);

    @Transactional
    @Modifying
    @Query("UPDATE ProdutoEntity p SET p.ativo = false WHERE p.id = :idProduto")
    void deleteByIdProduto(String idProduto);

    List<ProdutoEntity> findAllByAtivoTrue();

    List<ProdutoEntity> findAllByAtivoTrueAndCategoria(CategoriaEntity categoria);

}