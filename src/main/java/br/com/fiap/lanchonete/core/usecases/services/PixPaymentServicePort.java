package br.com.fiap.lanchonete.core.usecases.services;

import br.com.fiap.lanchonete.interfaceadapters.dtos.PixPaymentDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PixPaymentResponseDto;

public interface PixPaymentServicePort {

    public PixPaymentResponseDto processPayment(PixPaymentDto pixPaymentDTO);
}
