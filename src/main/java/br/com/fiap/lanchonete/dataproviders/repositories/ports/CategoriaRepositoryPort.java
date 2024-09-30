package br.com.fiap.lanchonete.dataproviders.repositories.ports;

import java.util.List;

import br.com.fiap.lanchonete.interfaceadapters.dtos.CategoriaDto;

public interface CategoriaRepositoryPort {

    List<CategoriaDto> findAll();

}