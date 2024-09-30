package br.com.fiap.lanchonete.interfaceadapters.mappers;

import br.com.fiap.lanchonete.core.entities.PedidoEntity;
import br.com.fiap.lanchonete.interfaceadapters.dtos.CategoriaDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.ClienteDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoProdutoDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoResponseDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.ProdutoDto;

public class PedidoResponseMapper {

    private PedidoResponseMapper() {
    }

    public static PedidoResponseDto map(PedidoEntity entity) {
        var pedidosProdutoDto = entity.getProdutos().stream()
                .map(pedProdEntity -> {
                    var categoriaDto = CategoriaDto.builder()
                            .id(pedProdEntity.getProduto().getCategoria().getId())
                            .nome(pedProdEntity.getProduto().getCategoria().getNome())
                            .build();

                    var produtoDto = ProdutoDto.builder()
                            .id(pedProdEntity.getProduto().getId())
                            .nome(pedProdEntity.getProduto().getNome())
                            .descricao(pedProdEntity.getProduto().getDescricao())
                            .categoria(categoriaDto)
                            .preco(pedProdEntity.getProduto().getPreco())
                            .build();

                    return PedidoProdutoDto.builder()
                            .id(pedProdEntity.getId())
                            .comboNum(pedProdEntity.getComboNum())
                            .preco(pedProdEntity.getPreco())
                            .produto(produtoDto)
                            .pedido(entity.getId())
                            .build();

                })
                .toList();
        var clienteDto = entity.getCliente() == null ? null : ClienteDto.builder()
                                                                .cpf(entity.getCliente().getCpf())
                                                                .nome(entity.getCliente().getNome())
                                                                .email(entity.getCliente().getEmail())
                                                                .build();
        return PedidoResponseDto.builder()
                .id(entity.getId())
                .dataHora(entity.getDataHora())
                .status(entity.getStatus())
                .valor(entity.getValor())
                .produtos(pedidosProdutoDto)
                .cliente(clienteDto)
                .build();
    }

}
