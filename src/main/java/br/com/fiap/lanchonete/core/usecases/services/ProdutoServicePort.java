package br.com.fiap.lanchonete.core.usecases.services;

import br.com.fiap.lanchonete.interfaceadapters.dtos.ProdutoDto;

import java.util.List;
import java.util.Optional;

public interface ProdutoServicePort {
    ProdutoDto save(ProdutoDto produtoDto);

    Optional<ProdutoDto> findByIdProduto(String idProduto);

    List<ProdutoDto> findAll();

    void deleteByIdProduto(String idProdutoDto);

    ProdutoDto saveOrUpdate(ProdutoDto produtoDto);

    List<ProdutoDto> findAllByAtivoTrue();

    List<ProdutoDto> findByCategoria(Integer idCategoria);
}
