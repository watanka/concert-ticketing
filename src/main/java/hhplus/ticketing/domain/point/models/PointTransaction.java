package hhplus.ticketing.domain.point.models;

import lombok.Getter;

public record PointTransaction(
        long transactionId,
        long userId,
        long amount,
        PointType type
) {
}
