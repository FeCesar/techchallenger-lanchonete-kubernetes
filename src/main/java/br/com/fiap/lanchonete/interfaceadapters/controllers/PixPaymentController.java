package br.com.fiap.lanchonete.interfaceadapters.controllers;

import br.com.fiap.lanchonete.core.usecases.services.impl.PixPaymentServiceImpl;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PixPaymentDto;
import br.com.fiap.lanchonete.interfaceadapters.dtos.PixPaymentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lanchonete/v1/pagamentos")
public class PixPaymentController {

    private final PixPaymentServiceImpl pagamentoService;

    public PixPaymentController(PixPaymentServiceImpl pixPaymentService) {
        this.pagamentoService = pixPaymentService;
    }

    @PostMapping("/processar")
    @Operation(summary = "Endpoint para processamento de pagamento via MercadoPago", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento processado com sucesso")
    })
    public ResponseEntity<PixPaymentResponseDto> processPayment(@RequestBody @Valid PixPaymentDto pixPaymentDTO) {
        PixPaymentResponseDto payment = pagamentoService.processPayment(pixPaymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
