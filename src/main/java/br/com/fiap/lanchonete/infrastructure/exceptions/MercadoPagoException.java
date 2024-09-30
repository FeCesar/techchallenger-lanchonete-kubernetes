package br.com.fiap.lanchonete.infrastructure.exceptions;

public class MercadoPagoException extends RuntimeException {
    public MercadoPagoException(String message) {
        super(message);
    }
}
