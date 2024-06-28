package hhplus.ticketing.api.payment.dto;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import lombok.Builder;

@Builder
public record PaymentTransactionResponse(long ticketId, String transactionTime, long orderTotal) {
    public static PaymentTransactionResponse from(PaymentTransaction paymentTransaction) {
        return PaymentTransactionResponse.builder()
                .ticketId(paymentTransaction.ticketId())
                .transactionTime(paymentTransaction.createAt().toString())
                .orderTotal(paymentTransaction.price())
                .build();
    }
}
