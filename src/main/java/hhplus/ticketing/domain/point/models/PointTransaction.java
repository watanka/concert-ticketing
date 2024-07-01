package hhplus.ticketing.domain.point.models;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointTransaction(
        long id,
        long userId,
        LocalDateTime transactionTime,
        long amount,
        PointType type
) {
    public PointTransaction(long userId, LocalDateTime transactionTime, long amount, PointType type) {
        this(0, userId, transactionTime, amount, type);
    }
}
