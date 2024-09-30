package br.com.fiap.lanchonete.core.usecases.services;

import br.com.fiap.lanchonete.interfaceadapters.dtos.ClienteDto;

public interface ClienteServicePort {
    ClienteDto findByCpfCliente (String cpfCliente);
    ClienteDto save(ClienteDto clienteDTO);
}
