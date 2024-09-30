package br.com.fiap.lanchonete.core.usecases.services.impl;

import br.com.fiap.lanchonete.core.usecases.services.CategoriaServicePort;
import br.com.fiap.lanchonete.interfaceadapters.dtos.CategoriaDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.CategoriaRepositoryPort;

import java.util.List;

public class CategoriaServiceImpl implements CategoriaServicePort {

    private CategoriaRepositoryPort categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepositoryPort categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaDto> findAll() {
        return categoriaRepository.findAll();
    }

}