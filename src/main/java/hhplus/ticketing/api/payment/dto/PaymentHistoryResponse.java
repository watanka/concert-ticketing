package hhplus.ticketing.api.payment.dto;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;

import java.util.List;

public record PaymentHistoryResponse(List<PaymentTransactionResponse> paymentHistory) {

    public static PaymentHistoryResponse from(List<PaymentTransaction> paymentList){
        return new PaymentHistoryResponse(
                paymentList.stream()
                        .map(PaymentTransactionResponse::from)
                        .toList()
        );
    }
}
