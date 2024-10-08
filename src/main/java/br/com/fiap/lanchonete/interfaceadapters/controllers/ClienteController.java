package br.com.fiap.lanchonete.interfaceadapters.controllers;


import br.com.fiap.lanchonete.core.usecases.services.ClienteServicePort;
import br.com.fiap.lanchonete.core.usecases.services.impl.ClienteServiceImpl;
import br.com.fiap.lanchonete.interfaceadapters.dtos.ClienteDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ClienteRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/lanchonete/v1/clientes")
@Tag(name = "Clientes", description = "Clientes")
public class ClienteController {

    private final ClienteServicePort clienteService;

    public ClienteController(ClienteRepositoryPort clienteRepository) {
        this.clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @GetMapping(value = "/{cpfCliente}", produces = {"application/json"})
    @Operation(description = "Consulta o cliente por CPF")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ClienteDto.class)))
    @ApiResponse(responseCode = "404", description = "Não encontrado")
    public ResponseEntity<ClienteDto> getClienteByCpf(@PathVariable String cpfCliente) {
        ClienteDto clienteDTO = clienteService.findByCpfCliente(cpfCliente);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDTO);
    }

    @PostMapping
    @Operation(description = "Cadastra um novo cliente")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ClienteDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<ClienteDto> createCliente(@Valid @RequestBody ClienteDto clienteDTO) {
        ClienteDto savedClienteDTO = clienteService.save(clienteDTO);
        return ResponseEntity.ok(savedClienteDTO);
    }

}