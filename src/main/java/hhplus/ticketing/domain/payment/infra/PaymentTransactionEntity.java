package hhplus.ticketing.domain.payment.infra;


import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="payment")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="user_id")
    long userId;

    @Column(name="price")
    long price;

    @Column(name="ticket_id")
    long ticketId;

    @Column(name="create_at")
    LocalDateTime createdAt;


    public static PaymentTransactionEntity from(PaymentTransaction paymentTransaction) {
        return PaymentTransactionEntity.builder()
                .userId(paymentTransaction.userId())
                .price(paymentTransaction.price())
                .ticketId(paymentTransaction.ticketId())
                .createdAt(paymentTransaction.createAt())
                .build();
    }

    public static PaymentTransaction to(PaymentTransactionEntity entity) {
        return PaymentTransaction.builder()
                .userId(entity.getUserId())
                .price(entity.getPrice())
                .ticketId(entity.getTicketId())
                .createAt(entity.getCreatedAt())
                .build();
    }
}
