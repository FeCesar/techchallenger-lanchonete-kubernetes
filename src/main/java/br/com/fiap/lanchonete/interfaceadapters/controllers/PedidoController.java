package br.com.fiap.lanchonete.interfaceadapters.controllers;


import java.util.List;


import br.com.fiap.lanchonete.core.entities.enums.StatusPedido;
import br.com.fiap.lanchonete.infrastructure.database.repositories.PedidoJpaRepository;
import br.com.fiap.lanchonete.infrastructure.exceptions.ObjectNotFoundException;
import br.com.fiap.lanchonete.infrastructure.exceptions.RegraNegocioException;
import br.com.fiap.lanchonete.core.usecases.services.PedidoServicePort;
import br.com.fiap.lanchonete.core.usecases.services.impl.PedidoServiceImpl;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoCheckoutDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoResponseDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ClienteRepositoryPort;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.PedidoRepositoryPort;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ProdutoRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lanchonete/v1/pedidos")
@Tag(name = "Pedidos", description = "Pedidos de clientes")
public class PedidoController {

    private final PedidoServicePort pedidoService;
    private PedidoController(PedidoRepositoryPort pedidoRepository, ClienteRepositoryPort clienteRepository, ProdutoRepositoryPort produtoRepository, PedidoJpaRepository jpaRepository) {
        this.pedidoService = new PedidoServiceImpl(pedidoRepository, jpaRepository, clienteRepository, produtoRepository);
    }

    @PostMapping
    @Operation(description = "Gera o pedido do cliente")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PedidoDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<?> createPedido(@RequestBody PedidoDto pedidoDto) {
        try{
            PedidoResponseDto savedPedido = pedidoService.save(pedidoDto);
            return ResponseEntity.ok(savedPedido);
        }catch(RegraNegocioException rne){
            return ResponseEntity.badRequest().body(rne.getMessage());
        }
    }
    @GetMapping
    @Operation(description = "Lista todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PedidoResponseDto.class)))
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<PedidoResponseDto> pedidos = pedidoService.findAllComProdutos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiza a busca de Pedido por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisicao invalidos"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de dados")
    })
    public ResponseEntity<PedidoResponseDto>findById(@PathVariable String id) {

        try {
            PedidoResponseDto obj = pedidoService.findById(id);
            return ResponseEntity.ok().body(obj);

        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Pedido não encontrado pelo Id: " + id);

        }

    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Informa o Status de Pedido por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisicao invalidos"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de dados")
    })
    public ResponseEntity<StatusPedido> getStatusFindById(@PathVariable String id) {
        try {
            PedidoResponseDto obj = pedidoService.findById(id);
            return ResponseEntity.ok().body(obj.getStatus());

        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Status não pode ser informado devido Pedido não ter sido encontrado pelo Id: " + id);

        }

    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Realiza a atualização de Status de Pedido", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do Pedido atualizado com sucesso")
    })
    public ResponseEntity<PedidoCheckoutDto> atualizarStatus(@PathVariable String id, @RequestParam StatusPedido status) {
        pedidoService.updateStatus(id, status);
        if(status == StatusPedido.PRONTO) {
            return ResponseEntity.ok(new PedidoCheckoutDto(pedidoService.findById(id).getId()));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
