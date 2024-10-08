package br.com.fiap.lanchonete.dataproviders.repositories.ports.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.fiap.lanchonete.infrastructure.database.repositories.PedidoJpaRepository;
import br.com.fiap.lanchonete.infrastructure.exceptions.ObjectNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PedidoResponseDto;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.PedidoRepositoryPort;
import br.com.fiap.lanchonete.core.entities.ClienteEntity;
import br.com.fiap.lanchonete.core.entities.PedidoEntity;
import br.com.fiap.lanchonete.core.entities.PedidoProdutoEntity;
import br.com.fiap.lanchonete.core.entities.ProdutoEntity;
import br.com.fiap.lanchonete.interfaceadapters.mappers.PedidoResponseMapper;
import br.com.fiap.lanchonete.core.entities.enums.StatusPedido;

@Repository
public class PedidoRepositoryImp implements PedidoRepositoryPort {

    @Autowired
    private PedidoJpaRepository pedidoJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PedidoResponseDto save(PedidoDto pedidoDto) {

        //PedidoEntity pedidoEntity = modelMapper.map(pedidoDto, PedidoEntity.class);
        
        ClienteEntity clienteEntity = StringUtils.isBlank(pedidoDto.getCpfCliente()) ? null : ClienteEntity.builder()
                                                                                                .cpf(pedidoDto.getCpfCliente())
                                                                                                .build();

        PedidoEntity pedidoEntity = PedidoEntity.builder()
                                            .id(UUID.randomUUID().toString())
                                            .cliente(clienteEntity)
                                            .dataHora(LocalDateTime.now())
                                            .status(StatusPedido.RECEBIDO)
                                            .valor(pedidoDto.getValor())
                                            .build();

        PedidoEntity savedEntity = pedidoJpaRepository.save(pedidoEntity);

        List<PedidoProdutoEntity> listaPedidoProduto = new ArrayList<>();

        Optional.ofNullable(pedidoDto.getProdutos()).orElse(Collections.emptyList()).forEach(p ->{
            if (p.possuiLanche()){
                listaPedidoProduto.add(
                                        PedidoProdutoEntity.builder()
                                                    .comboNum(p.getComboNum())
                                                    .pedido(pedidoEntity)
                                                    .preco(p.getLanche().getPreco())
                                                    .produto(ProdutoEntity.builder().id(p.getLanche().getId()).build())
                                                    .build()
                                    );
                
            }

            if (p.possuiAcompanhamento()){
                listaPedidoProduto.add(
                                        PedidoProdutoEntity.builder()
                                                    .comboNum(p.getComboNum())
                                                    .pedido(pedidoEntity)
                                                    .preco(p.getAcompanhamento().getPreco())
                                                    .produto(ProdutoEntity.builder().id(p.getAcompanhamento().getId()).build())
                                                    .build()
                                    );
            }

            if (p.possuiBebida()){
                listaPedidoProduto.add(
                                        PedidoProdutoEntity.builder()
                                                    .comboNum(p.getComboNum())
                                                    .pedido(pedidoEntity)
                                                    .preco(p.getBebida().getPreco())
                                                    .produto(ProdutoEntity.builder().id(p.getBebida().getId()).build())
                                                    .build()
                                    );
            }

            if (p.possuiSobremesa()){
                listaPedidoProduto.add(
                                        PedidoProdutoEntity.builder()
                                                    .comboNum(p.getComboNum())
                                                    .pedido(pedidoEntity)
                                                    .preco(p.getSobremesa().getPreco())
                                                    .produto(ProdutoEntity.builder().id(p.getSobremesa().getId()).build())
                                                    .build()
                                    );
            }
        });


        savedEntity.setProdutos(listaPedidoProduto);

        savedEntity = pedidoJpaRepository.save(savedEntity);
        
        return PedidoResponseMapper.map(savedEntity);
    }

    @Override
    public List<PedidoDto> findAll() {
        return pedidoJpaRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, PedidoDto.class))
                .toList();
    }

    public List<PedidoResponseDto> findAllComProdutos(){
        List<PedidoEntity> listaPedidosComProdutos = pedidoJpaRepository.findAllComProdutos();
        
        return listaPedidosComProdutos
                    .stream()
                    .map(PedidoResponseMapper::map)
                    .toList();
    }

    @Override
    public PedidoResponseDto findById(String id) {
        PedidoEntity pedidoEntity = pedidoJpaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id: " + id));

        return PedidoResponseMapper.map(pedidoEntity);

    }

}
