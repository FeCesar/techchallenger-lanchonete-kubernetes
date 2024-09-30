package br.com.fiap.lanchonete.interfaceadapters.dtos;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Getter;

@Getter
public class PedidoDto {
    
    private String cpfCliente;
    
    @Schema(accessMode = AccessMode.READ_ONLY)
    private BigDecimal valor;
    
    private List<ComboDto> produtos;

    public PedidoDto(){}

    public PedidoDto(String cpfCliente, BigDecimal valor, List<ComboDto> produtos) {
        this.cpfCliente = cpfCliente;
        this.valor = valor;
        this.produtos = produtos;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setProdutos(List<ComboDto> produtos) {
        this.produtos = produtos;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String cpfCliente;
        private BigDecimal valor;
        private List<ComboDto> produtos;
        
        public Builder setCpfCliente(String cpfCliente) {
            this.cpfCliente = cpfCliente;
            return this;
        }
        public Builder setValor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }
        public Builder setProdutos(List<ComboDto> produtos) {
            this.produtos = produtos;
            return this;
        }

        public PedidoDto build(){
            return new PedidoDto(cpfCliente, valor, produtos);
        }

    }

    
}
