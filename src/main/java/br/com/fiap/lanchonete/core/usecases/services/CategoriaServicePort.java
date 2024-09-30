package br.com.fiap.lanchonete.core.usecases.services;

import java.util.List;

import br.com.fiap.lanchonete.interfaceadapters.dtos.CategoriaDto;

public interface CategoriaServicePort {

    List<CategoriaDto> findAll();

}
