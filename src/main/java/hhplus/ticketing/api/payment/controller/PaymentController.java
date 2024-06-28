package hhplus.ticketing.api.payment.controller;

import hhplus.ticketing.api.payment.dto.PaymentHistoryResponse;
import hhplus.ticketing.api.payment.dto.PaymentRequest;
import hhplus.ticketing.api.payment.dto.PaymentTransactionResponse;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PaymentController {

    @GetMapping("/payment_history")
    public PaymentHistoryResponse viewPaymentHistory(@RequestParam(name="userId") long userId){

        LocalDateTime date = LocalDateTime.of(2024, 6, 27, 0, 30);

        return PaymentHistoryResponse.from(
                List.of(
                    new PaymentTransaction(1, 200000, 1, date)
                )
        );
    }


    @PostMapping("/payments")
    public PaymentTransactionResponse payTicket(@RequestBody PaymentRequest paymentRequest){
        LocalDateTime date = LocalDateTime.of(2024, 6, 27, 0, 30);

        return PaymentTransactionResponse.from(
                new PaymentTransaction(1, 200000, 3, date)
            );
        }




}
