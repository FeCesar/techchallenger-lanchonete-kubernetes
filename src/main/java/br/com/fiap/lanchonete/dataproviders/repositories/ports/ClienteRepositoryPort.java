package br.com.fiap.lanchonete.dataproviders.repositories.ports;

import br.com.fiap.lanchonete.interfaceadapters.dtos.ClienteDto;

public interface ClienteRepositoryPort {
    ClienteDto findByCpfCliente(String cpfCliente);
    ClienteDto save(ClienteDto clienteDTO);
}
