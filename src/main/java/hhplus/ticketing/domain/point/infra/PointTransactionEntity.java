package hhplus.ticketing.domain.point.infra;

import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="point_transaction")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointTransactionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name="user_id")
    long userId;
    @Column(name="transaction_time")
    LocalDateTime transactionTime;
    @Column
    long amount;
    @Enumerated(EnumType.STRING)
    @Column
    PointType type;

    public static PointTransactionEntity from(PointTransaction pointTransaction) {
        return PointTransactionEntity.builder()
                .userId(pointTransaction.getUserId())
                .transactionTime(pointTransaction.getTransactionTime())
                .amount(pointTransaction.getAmount())
                .type(pointTransaction.getType())
                .build();
    }

    public static PointTransaction to(PointTransactionEntity entity) {
        return PointTransaction.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .transactionTime(entity.getTransactionTime())
                .amount(entity.getAmount())
                .type(entity.getType())
                .build();
    }
}
