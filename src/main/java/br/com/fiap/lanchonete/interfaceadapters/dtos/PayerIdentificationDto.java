package br.com.fiap.lanchonete.interfaceadapters.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PayerIdentificationDto {
    @NotNull
    private String type;

    @NotNull
    private String number;

    public PayerIdentificationDto() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
