package br.com.fiap.lanchonete.dataproviders.repositories.ports.impl;

import br.com.fiap.lanchonete.infrastructure.database.repositories.ClienteJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiap.lanchonete.interfaceadapters.dtos.ClienteDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ClienteRepositoryPort;
import br.com.fiap.lanchonete.core.entities.ClienteEntity;

@Repository
public class ClienteRepositoryImp implements ClienteRepositoryPort {

    @Autowired
    private ClienteJpaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClienteDto findByCpfCliente(String cpfCliente) {
        return repository.findById(cpfCliente)
                .map(entity -> modelMapper.map(entity, ClienteDto.class))
                .orElse(null);
    }

    @Override
    public ClienteDto save(ClienteDto clienteDto) {
        ClienteEntity entity = modelMapper.map(clienteDto, ClienteEntity.class);
        ClienteEntity savedEntity = repository.save(entity);
        return modelMapper.map(savedEntity, ClienteDto.class);
    }

}
