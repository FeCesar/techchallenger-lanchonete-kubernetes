package br.com.fiap.lanchonete.core.entities.enums;

import lombok.Getter;

@Getter
public enum StatusPedido {
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO
}
