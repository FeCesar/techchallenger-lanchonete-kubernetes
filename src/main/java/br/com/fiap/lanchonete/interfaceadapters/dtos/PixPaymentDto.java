package br.com.fiap.lanchonete.interfaceadapters.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PixPaymentDto {
    @NotNull
    private BigDecimal transactionAmount;

    @NotNull
    @JsonProperty("description")
    private String productDescription;

    @NotNull
    private PayerDto payer;

    public PixPaymentDto() {
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setPayer(PayerDto payer) {
        this.payer = payer;
    }
}
