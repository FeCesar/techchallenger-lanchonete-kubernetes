package br.com.fiap.lanchonete.interfaceadapters.dtos;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PedidoProdutoDto {

    private Integer id;
    private String pedido;
    private ProdutoDto produto;
    private BigDecimal preco;
    private Integer comboNum;
    
    public PedidoProdutoDto(){}

    /**
     * 
     * @param id
     * @param pedido
     * @param produto
     * @param preco
     * @param comboNum
     */
    public PedidoProdutoDto(Integer id, String pedido, ProdutoDto produto, BigDecimal preco, Integer comboNum) {
        this.id = id;
        this.pedido = pedido;
        this.produto = produto;
        this.preco = preco;
        this.comboNum = comboNum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public void setProduto(ProdutoDto produto) {
        this.produto = produto;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setComboNum(Integer comboNum) {
        this.comboNum = comboNum;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String pedido;
        private ProdutoDto produto;
        private BigDecimal preco;
        private Integer comboNum;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder pedido(String pedido) {
            this.pedido = pedido;
            return this;
        }

        public Builder produto(ProdutoDto produto) {
            this.produto = produto;
            return this;
        }

        public Builder preco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public Builder comboNum(Integer comboNum) {
            this.comboNum = comboNum;
            return this;
        }

        public PedidoProdutoDto build() {
            return new PedidoProdutoDto(id, pedido, produto, preco, comboNum);
        }
    }
    
}


