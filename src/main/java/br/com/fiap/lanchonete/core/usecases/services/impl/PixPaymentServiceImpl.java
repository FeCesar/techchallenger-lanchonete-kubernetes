package br.com.fiap.lanchonete.core.usecases.services.impl;

import br.com.fiap.lanchonete.core.usecases.services.PixPaymentServicePort;
import br.com.fiap.lanchonete.infrastructure.exceptions.MercadoPagoException;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PixPaymentDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PixPaymentResponseDto;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PixPaymentServiceImpl implements PixPaymentServicePort {

    @Value("${mercadopago.access_token}")
    private String mercadoPagoAccessToken;

    public PixPaymentResponseDto processPayment(PixPaymentDto pixPaymentDTO) {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PaymentClient paymentClient = new PaymentClient();

            PaymentCreateRequest paymentCreateRequest =
                    PaymentCreateRequest.builder()
                            .transactionAmount(pixPaymentDTO.getTransactionAmount())
                            .description(pixPaymentDTO.getProductDescription())
                            .paymentMethodId("pix")
                            .payer(
                                    PaymentPayerRequest.builder()
                                            .email(pixPaymentDTO.getPayer().getEmail())
                                            .firstName(pixPaymentDTO.getPayer().getFirstName())
                                            .lastName(pixPaymentDTO.getPayer().getLastName())
                                            .identification(
                                                    IdentificationRequest.builder()
                                                            .type(pixPaymentDTO.getPayer().getIdentification().getType())
                                                            .number(pixPaymentDTO.getPayer().getIdentification().getNumber())
                                                            .build())
                                            .build())
                            .build();

            Payment createdPayment = paymentClient.create(paymentCreateRequest);

            return new PixPaymentResponseDto(
                    createdPayment.getId(),
                    String.valueOf(createdPayment.getStatus()),
                    createdPayment.getStatusDetail(),
                    createdPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64(),
                    createdPayment.getPointOfInteraction().getTransactionData().getQrCode());
        } catch (MPApiException apiException) {
            throw new MercadoPagoException("Erro no processamento do pagamento via MercadoPago: " + apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            throw new MercadoPagoException("Erro no processamento do pagamento via MercadoPago: " + exception.getMessage());
        }
    }
}
