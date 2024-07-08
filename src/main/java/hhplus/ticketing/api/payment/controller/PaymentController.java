package hhplus.ticketing.api.payment.controller;

import hhplus.ticketing.api.payment.dto.PaymentRequest;
import hhplus.ticketing.api.payment.dto.PaymentTransactionResponse;
import hhplus.ticketing.api.payment.facade.PaymentFacade;
import hhplus.ticketing.domain.payment.components.PaymentService;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name="결제", description="예약 티켓을 결제합니다.")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    @Autowired
    private final PaymentFacade paymentFacade;

    @Operation(summary="결제내역 조회", description="결제 내역을 조회합니다.")
    @GetMapping("/payment_history")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = PaymentTransactionResponse.class))))
    public List<PaymentTransactionResponse> viewPaymentHistory(@RequestParam(name="userId") long userId){

        List<PaymentTransaction> paymentTransactionList = paymentService.findTransactionHistory(userId);
        return paymentTransactionList.stream()
                .map(PaymentTransactionResponse::from)
                .toList();

    }

    @Operation(summary="결제 진행", description="예약 티켓을 결제합니다.")
    @PostMapping("/payments")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PaymentTransactionResponse.class)))
    public PaymentTransactionResponse payTicket(@RequestBody PaymentRequest paymentRequest){

        PaymentTransaction paymentTransaction = paymentFacade.processPayment(paymentRequest.ticketId(), paymentRequest.price(), paymentRequest.userId(), LocalDateTime.now());

        return PaymentTransactionResponse.from(paymentTransaction);

        }




}
