package br.com.fiap.lanchonete.interfaceadapters.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PayerDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private PayerIdentificationDto identification;

    public PayerDto() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdentification(PayerIdentificationDto identification) {
        this.identification = identification;
    }
}
