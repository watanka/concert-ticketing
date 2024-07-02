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
        //TODO: possible danger-DB에서 id값이 업데이트되긴 하지만, 도메인모델에서 id를 0으로 전부 설정하는 것이 안전한 방법은 아니다.
        this(0, userId, transactionTime, amount, type);
    }
}
