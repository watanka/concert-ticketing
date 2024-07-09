package hhplus.ticketing.domain.point.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

//@Builder
//public record PointTransaction(
//        long id,
//        long userId,
//        LocalDateTime transactionTime,
//        long amount,
//        PointType type
//) {
//
//    public PointTransaction(long userId, LocalDateTime transactionTime, long amount, PointType type) {
//        //TODO: possible danger-DB에서 id값이 업데이트되긴 하지만, 도메인모델에서 id를 0으로 전부 설정하는 것이 안전한 방법은 아니다.
//        this(0, userId, transactionTime, amount, type);
//    }
//}

@Builder
@AllArgsConstructor
@Getter
public class PointTransaction {
    long id;
    long userId;
    LocalDateTime transactionTime;
    long amount;
    PointType type;


    public PointTransaction(long userId, LocalDateTime transactionTime, long amount, PointType type) {
        this.userId = userId;
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.type = type;
    }
}