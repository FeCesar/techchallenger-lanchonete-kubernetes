package br.com.fiap.lanchonete.infrastructure.exceptions;

public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
