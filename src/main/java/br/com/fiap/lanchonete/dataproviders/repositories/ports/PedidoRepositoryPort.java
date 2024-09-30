package br.com.fiap.lanchonete.dataproviders.repositories.ports;

import br.com.fiap.lanchonete.core.entities.PedidoEntity;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoResponseDto;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryPort {
    PedidoResponseDto save(PedidoDto pedidoDto); // Create and Update
    List<PedidoDto> findAll(); // Read All
    List<PedidoResponseDto> findAllComProdutos(); // le todos, trazendo os produtos
    public PedidoResponseDto findById(String id);
}
