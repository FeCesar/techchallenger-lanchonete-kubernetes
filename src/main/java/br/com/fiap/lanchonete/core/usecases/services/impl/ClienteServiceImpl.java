package br.com.fiap.lanchonete.core.usecases.services.impl;

import br.com.fiap.lanchonete.core.usecases.services.ClienteServicePort;
import br.com.fiap.lanchonete.interfaceadapters.dtos.ClienteDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ClienteRepositoryPort;

public class ClienteServiceImpl implements ClienteServicePort {

    private ClienteRepositoryPort clienteRepository;

    public ClienteServiceImpl(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDto findByCpfCliente(String cpfCliente) {
        return clienteRepository.findByCpfCliente(cpfCliente);
    }

    @Override
    public ClienteDto save(ClienteDto clienteDto) { return clienteRepository.save(clienteDto); }
}