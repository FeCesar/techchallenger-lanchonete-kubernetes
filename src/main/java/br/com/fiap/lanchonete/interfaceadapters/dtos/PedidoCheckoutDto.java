package br.com.fiap.lanchonete.interfaceadapters.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PedidoCheckoutDto {

    private final String pedidoId;

    public PedidoCheckoutDto(String pedidoId){
        this.pedidoId = pedidoId;
    }

}
