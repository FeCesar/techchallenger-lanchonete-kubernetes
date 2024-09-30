package br.com.fiap.lanchonete.interfaceadapters.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.lanchonete.core.entities.enums.StatusPedido;
import lombok.Getter;

@Getter
public class PedidoResponseDto {

    private String id;
    private ClienteDto cliente;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private StatusPedido status;
    private List<PedidoProdutoDto> produtos = new ArrayList<>();
    

    public PedidoResponseDto(){}

    /**
     * 
     * @param id
     * @param cliente
     * @param valor
     * @param dataHora
     * @param status
     * @param produtos
     */
    public PedidoResponseDto(String id, ClienteDto cliente, BigDecimal valor, LocalDateTime dataHora,
            StatusPedido status, List<PedidoProdutoDto> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.dataHora = dataHora;
        this.status = status;
        this.produtos = produtos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCliente(ClienteDto cliente) {
        this.cliente = cliente;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public void setProdutos(List<PedidoProdutoDto> produtos) {
        this.produtos = produtos;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String id;
        private ClienteDto cliente;
        private BigDecimal valor;
        private LocalDateTime dataHora;
        private StatusPedido status;
        private List<PedidoProdutoDto> produtos = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder cliente(ClienteDto cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder valor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public Builder dataHora(LocalDateTime dataHora) {
            this.dataHora = dataHora;
            return this;
        }

        public Builder status(StatusPedido status) {
            this.status = status;
            return this;
        }

        public Builder produtos(List<PedidoProdutoDto> produtos) {
            this.produtos = produtos;
            return this;
        }

        public PedidoResponseDto build() {
            return new PedidoResponseDto(id, cliente, valor, dataHora, status, produtos);
        }
    }

    
}
