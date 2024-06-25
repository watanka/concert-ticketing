package hhplus.ticketing.point.unit;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.point.models.User;
import hhplus.ticketing.domain.point.infra.MemoryPointRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointTransactionTest {

    private User setUser(long userId, long balance){

        User user = new User(userId);
        user.rechargePoint(balance);

        return user;
    }



    @Test
    @DisplayName("포인트를 충전한다.")
    void recharge_point(){
        long pointAmount = 10000;
        User user = setUser(1, pointAmount);

        Assertions.assertThat(user.getBalance()).isEqualTo(pointAmount);

    }

    @Test
    @DisplayName("포인트를 사용한다.")
    void use_point(){
        //given
        long pointAmount = 10000;
        long usePoint = 5000;

        User user = setUser(1, pointAmount);
        //when
        user.usePoint(usePoint);
        //then
        Assertions.assertThat(user.getBalance()).isEqualTo(pointAmount-usePoint);
    }

    @Test
    @DisplayName("포인트가 부족할 경우 에러가 발생한다.")
    void cannot_use_point_larger_than_balance(){
        long balance = 1000;
        long usePoint = 3000;

        User user = setUser(1, balance);

        assertThrows(IllegalArgumentException.class,
                () -> user.usePoint(usePoint));
    }

    @Test
    @DisplayName("포인트 충전/사용 내역이 기록된다.")
    void record_point_transaction_history(){
        MemoryPointRepository memoryPointRepository = new MemoryPointRepository();
        PointService pointService = new PointService(memoryPointRepository);

        long userId = 1;

        User user = setUser(userId, 0);
        long rechargePoint = 50000;
        long usePoint = 30000;

        pointService.rechargePoint(user, rechargePoint);
        pointService.usePoint(user, usePoint);

        Assertions.assertThat(pointService.queryTransactions(userId))
                .isEqualTo(
                        List.of(
                                new PointTransaction(1, userId,50000, PointType.RECHARGE),
                                new PointTransaction(1, userId,30000, PointType.USE)
                        ));
    }

}
