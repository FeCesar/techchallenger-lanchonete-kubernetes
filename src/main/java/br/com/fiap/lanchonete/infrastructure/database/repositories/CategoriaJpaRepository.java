package br.com.fiap.lanchonete.infrastructure.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.lanchonete.core.entities.CategoriaEntity;

public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Integer>{
}
