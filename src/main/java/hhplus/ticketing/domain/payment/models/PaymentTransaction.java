package hhplus.ticketing.domain.payment.models;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentTransaction(
        long userId,
        long price,
        long ticketId,
        LocalDateTime createAt

        ) {

}
