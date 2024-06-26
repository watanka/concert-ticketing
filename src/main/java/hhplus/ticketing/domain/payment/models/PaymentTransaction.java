package hhplus.ticketing.domain.payment.models;

public record PaymentTransaction(
        long userId,
        long price,
        long ticketId

        ) {

}
