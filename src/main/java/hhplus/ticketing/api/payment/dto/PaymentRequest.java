package hhplus.ticketing.api.payment.dto;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record PaymentRequest(
    long userId,
    long ticketId,
    long price
) {
    public static PaymentTransaction to(PaymentRequest request){
        return PaymentTransaction.builder()
                .userId(request.userId())
                .ticketId(request.ticketId())
                .price(request.price())
                .createAt(LocalDateTime.now())
                .build();
    }
}
