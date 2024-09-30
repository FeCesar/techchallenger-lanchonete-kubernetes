package br.com.fiap.lanchonete.infrastructure.database.repositories;

import br.com.fiap.lanchonete.core.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, String> {
}
