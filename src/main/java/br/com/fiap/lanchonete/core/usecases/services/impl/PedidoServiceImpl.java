package br.com.fiap.lanchonete.core.usecases.services.impl;

import br.com.fiap.lanchonete.core.entities.ClienteEntity;
import br.com.fiap.lanchonete.core.entities.PedidoEntity;
import br.com.fiap.lanchonete.core.entities.enums.StatusPedido;
import br.com.fiap.lanchonete.core.usecases.services.ClienteServicePort;
import br.com.fiap.lanchonete.core.usecases.services.PedidoServicePort;
import br.com.fiap.lanchonete.core.usecases.services.ProdutoServicePort;
import br.com.fiap.lanchonete.infrastructure.database.repositories.PedidoJpaRepository;
import br.com.fiap.lanchonete.infrastructure.exceptions.ObjectNotFoundException;
import br.com.fiap.lanchonete.infrastructure.exceptions.RegraNegocioException;
import br.com.fiap.lanchonete.interfaceadapters.dtos.*;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ClienteRepositoryPort;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.PedidoRepositoryPort;
import br.com.fiap.lanchonete.dataproviders.repositories.ports.ProdutoRepositoryPort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PedidoServiceImpl implements PedidoServicePort {

    private final PedidoRepositoryPort pedidoRepository;
    private final PedidoJpaRepository jpaRepository;
    private final ProdutoServicePort produtoService;
    private final ClienteServicePort clienteService;

    public PedidoServiceImpl(PedidoRepositoryPort pedidoRepository, PedidoJpaRepository jpaRepository, ClienteRepositoryPort clienteRepository, ProdutoRepositoryPort produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.jpaRepository = jpaRepository;
        this.clienteService = new ClienteServiceImpl(clienteRepository);
        this.produtoService = new ProdutoServiceImpl(produtoRepository);
    }


    @Override
    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll();
    }
    @Override
    public List<PedidoResponseDto> findAllComProdutos() {

        List<PedidoResponseDto> lista = pedidoRepository.findAllComProdutos();

        return lista
                .stream()
                .filter(pedido -> pedido.getStatus() != StatusPedido.FINALIZADO)
                .sorted(Comparator.comparing((PedidoResponseDto pedido) -> {
                    return switch (pedido.getStatus()) {
                        case PRONTO -> 1;
                        case EM_PREPARACAO -> 2;
                        case RECEBIDO -> 3;
                        default -> 4;
                    };
                }).thenComparing(PedidoResponseDto::getDataHora))
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDto findById(String id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public void updateStatus(String id, StatusPedido statusAtualizado) {
        PedidoEntity pedidoEntity = jpaRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado! Id: " + id));
        pedidoEntity.setStatus(statusAtualizado);
        jpaRepository.save(pedidoEntity);
    }

    @Override
    public PedidoResponseDto save(PedidoDto pedidoDto) {
        BigDecimal valorPedido = BigDecimal.ZERO;
        AtomicInteger comboNum = new AtomicInteger(1);

        var clienteEntity = validarCliente(pedidoDto);

        for (var p : Optional.ofNullable(pedidoDto.getProdutos()).orElse(Collections.emptyList())) {
            ComboDto combo = ComboDto.builder()
                    .lanche(p.getLanche())
                    .sobremesa(p.getSobremesa())
                    .bebida(p.getBebida())
                    .acompanhamento(p.getAcompanhamento())
                    .build();
            var lancheOptional = combo.possuiLanche() ? produtoService.findByIdProduto(combo.getLanche().getId()) : Optional.empty();
            var sobremesaOptional = combo.possuiSobremesa() ? produtoService.findByIdProduto(combo.getSobremesa().getId()) : Optional.empty();
            var bebidaOptional = combo.possuiBebida() ? produtoService.findByIdProduto(combo.getBebida().getId()) : Optional.empty();
            var acompanhamentoOptional = combo.possuiAcompanhamento() ? produtoService.findByIdProduto(combo.getAcompanhamento().getId()) : Optional.empty();

            validarProdutos(combo, (Optional<ProdutoDto>) lancheOptional, (Optional<ProdutoDto>) sobremesaOptional, (Optional<ProdutoDto>) bebidaOptional, (Optional<ProdutoDto>) acompanhamentoOptional);

            valorPedido = calcularValorPedido(combo, (Optional<ProdutoDto>) lancheOptional, (Optional<ProdutoDto>) sobremesaOptional, (Optional<ProdutoDto>) bebidaOptional, (Optional<ProdutoDto>) acompanhamentoOptional, valorPedido);
            combo.setComboNum(comboNum.getAndIncrement());
        }

        pedidoDto.setValor(valorPedido);
        return pedidoRepository.save(pedidoDto);
    }

    private ClienteDto validarCliente(PedidoDto pedidoDto) {
        boolean clienteInformado = pedidoDto.getCpfCliente() != null && !pedidoDto.getCpfCliente().isEmpty();
        var clienteEntity = clienteInformado ? clienteService.findByCpfCliente(pedidoDto.getCpfCliente()) : null;

        if (clienteInformado && clienteEntity == null) {
            throw new RegraNegocioException("Cliente não localizado");
        }
        return clienteEntity;
    }

    private void validarProdutos(ComboDto combo, Optional<ProdutoDto> lancheOptional, Optional<ProdutoDto> sobremesaOptional, Optional<ProdutoDto> bebidaOptional, Optional<ProdutoDto> acompanhamentoOptional) {
        boolean lancheValido = !combo.possuiLanche() || (lancheOptional.isPresent() && lancheOptional.get().isLanche());
        boolean sobremesaValido = !combo.possuiSobremesa() || (sobremesaOptional.isPresent() && sobremesaOptional.get().isSobremesa());
        boolean bebidaValido = !combo.possuiBebida() || (bebidaOptional.isPresent() && bebidaOptional.get().isBebida());
        boolean acompanhamentoValido = !combo.possuiAcompanhamento() || (acompanhamentoOptional.isPresent() && acompanhamentoOptional.get().isAcompanhamento());

        if (!(combo.possuiLanche() || combo.possuiSobremesa() || combo.possuiBebida() || combo.possuiAcompanhamento())) {
            throw new RegraNegocioException("É obrigatório ao menos um item no combo");
        }

        if (!(lancheValido && sobremesaValido && bebidaValido && acompanhamentoValido)) {
            throw new RegraNegocioException("Produtos inválidos ou inativos");
        }
    }

    private BigDecimal calcularValorPedido(ComboDto combo, Optional<ProdutoDto> lancheOptional, Optional<ProdutoDto> sobremesaOptional, Optional<ProdutoDto> bebidaOptional, Optional<ProdutoDto> acompanhamentoOptional, BigDecimal valorPedido) {
        if (combo.possuiLanche()) {
            combo.getLanche().setPreco(lancheOptional.get().getPreco());
            valorPedido = valorPedido.add(combo.getLanche().getPreco());
        }

        if (combo.possuiSobremesa()) {
            combo.getSobremesa().setPreco(sobremesaOptional.get().getPreco());
            valorPedido = valorPedido.add(combo.getSobremesa().getPreco());
        }

        if (combo.possuiBebida()) {
            combo.getBebida().setPreco(bebidaOptional.get().getPreco());
            valorPedido = valorPedido.add(combo.getBebida().getPreco());
        }

        if (combo.possuiAcompanhamento()) {
            combo.getAcompanhamento().setPreco(acompanhamentoOptional.get().getPreco());
            valorPedido = valorPedido.add(combo.getAcompanhamento().getPreco());
        }
        return valorPedido;
    }


}
