package hhplus.ticketing.point.unit;

import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.point.infra.MemoryPointRepository;
import hhplus.ticketing.domain.user.infra.MemoryUserRepository;
import hhplus.ticketing.domain.user.repository.UserRepository;
import hhplus.ticketing.domain.user.components.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointTransactionTest {

    UserRepository userRepository = new MemoryUserRepository();
    UserService userService = new UserService(userRepository);

    MemoryPointRepository memoryPointRepository = new MemoryPointRepository();
    PointService pointService = new PointService(memoryPointRepository);

    private User setUser(long userId, long balance){
        return new User(userId, balance);
    }




    @Test
    @DisplayName("포인트를 충전한다.")
    void recharge_point(){
        long pointAmount = 10000;
        Point point = new Point(pointAmount, PointType.RECHARGE);
        User user = setUser(1, 0);
        user.updatePoint(point);
        userService.save(user);

        User userFound = userService.findById(user.getUserId());
        Assertions.assertThat(userFound.getBalance()).isEqualTo(pointAmount);

    }

    @Test
    @DisplayName("포인트를 사용한다.")
    void use_point(){
        //given
        long pointAmount = 10000;
        Point usePoint = new Point( 5000, PointType.USE);

        User user = setUser(1, pointAmount);
        //when
        user.updatePoint(usePoint);
        userService.save(user);

        //then
        User userFound = userService.findById(user.getUserId());
        Assertions.assertThat(userFound.getBalance()).isEqualTo(pointAmount-usePoint.getAmount());
    }

    @Test
    @DisplayName("포인트가 부족할 경우 에러가 발생한다.")
    void cannot_use_point_larger_than_balance(){
        long balance = 1000;
        Point usePoint = new Point( 3000, PointType.USE);

        User user = setUser(1, balance);

        assertThrows(InsufficientBalanceException.class,
                () -> user.updatePoint(usePoint));
    }

    @Test
    @DisplayName("포인트 충전/사용 내역이 기록된다.")
    void record_point_transaction_history(){


        long userId = 1;

        User user = setUser(userId, 0);
        long rechargePointAmount = 50000;
        long usePointAmount = 30000;

        Point rechargePoint = new Point(rechargePointAmount, PointType.RECHARGE);
        Point usePoint = new Point(usePointAmount, PointType.USE);

        LocalDateTime now = LocalDateTime.now();

        pointService.recordPointTransaction(user.getUserId(), rechargePoint, now);
        pointService.recordPointTransaction(user.getUserId(), usePoint, now);

        Assertions.assertThat(pointService.queryTransactions(userId))
                .isEqualTo(
                        List.of(
                                new PointTransaction(userId, now, 50000, PointType.RECHARGE),
                                new PointTransaction(userId, now,30000, PointType.USE)
                        ));
    }

}
