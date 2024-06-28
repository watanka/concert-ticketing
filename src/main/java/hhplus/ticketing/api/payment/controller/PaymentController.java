package hhplus.ticketing.api.payment.controller;

import hhplus.ticketing.api.concert.dto.ConcertListResponse;
import hhplus.ticketing.api.payment.dto.PaymentHistoryResponse;
import hhplus.ticketing.api.payment.dto.PaymentRequest;
import hhplus.ticketing.api.payment.dto.PaymentTransactionResponse;
import hhplus.ticketing.api.watingqueue.dto.TokenResponse;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name="결제", description="예약 티켓을 결제합니다.")
@RestController
public class PaymentController {


    @Operation(summary="결제내역 조회", description="결제 내역을 조회합니다.")
    @GetMapping("/payment_history")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = PaymentHistoryResponse.class))))
    public PaymentHistoryResponse viewPaymentHistory(@RequestParam(name="userId") long userId){

        LocalDateTime date = LocalDateTime.of(2024, 6, 27, 0, 30);

        return PaymentHistoryResponse.from(
                List.of(
                    new PaymentTransaction(1, 200000, 1, date)
                )
        );
    }

    @Operation(summary="결제 진행", description="예약 티켓을 결제합니다.")
    @PostMapping("/payments")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PaymentTransactionResponse.class)))
    public PaymentTransactionResponse payTicket(@RequestBody PaymentRequest paymentRequest){
        LocalDateTime date = LocalDateTime.of(2024, 6, 27, 0, 30);

        return PaymentTransactionResponse.from(
                new PaymentTransaction(1, 200000, 3, date)
            );
        }




}
