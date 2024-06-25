package hhplus.ticketing.domain.point.models;

public record PointTransaction(
        long transactionId,
        long userId,
        long amount,
        PointType type
) {
}
