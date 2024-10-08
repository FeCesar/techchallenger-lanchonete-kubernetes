package br.com.fiap.lanchonete.dataproviders.repositories.ports.impl;

import br.com.fiap.lanchonete.core.entities.CategoriaEntity;
import br.com.fiap.lanchonete.core.entities.ProdutoEntity;
import br.com.fiap.lanchonete.infrastructure.database.repositories.ProdutoJpaRepository;
import br.com.fiap.lanchonete.interfaceadapters.dtos.ProdutoDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ProdutoRepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImp implements ProdutoRepositoryPort {

    @Autowired
    private ProdutoJpaRepository produtoJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProdutoDto save(ProdutoDto produtoDto) {
        ProdutoEntity produtoEntity = modelMapper.map(produtoDto, ProdutoEntity.class);
        ProdutoEntity savedEntity = produtoJpaRepository.save(produtoEntity);
        return modelMapper.map(savedEntity, ProdutoDto.class);
    }

    @Override
    public Optional<ProdutoDto> findByIdProduto(String idProduto) {
        return produtoJpaRepository.findById(idProduto)
                .map(entity -> modelMapper.map(entity, ProdutoDto.class));
    }

    @Override
    public List<ProdutoDto> findAll() {
        return produtoJpaRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, ProdutoDto.class))
                .toList();
    }

    @Override
    public void deleteByIdProduto(String idProduto) {
        produtoJpaRepository.deleteByIdProduto(idProduto);
    }

    @Override
    public List<ProdutoDto> findAllByAtivoTrue() {
        return produtoJpaRepository.findAllByAtivoTrue()
                .stream()
                .map(entity -> modelMapper.map(entity, ProdutoDto.class))
                .toList();
    }

    @Override
    public List<ProdutoDto> findAllByAtivoTrueAndCategoria(CategoriaEntity categoria) {
        return produtoJpaRepository.findAllByAtivoTrueAndCategoria(categoria)
                .stream()
                .map(entity -> modelMapper.map(entity, ProdutoDto.class))
                .toList();
    }

}
