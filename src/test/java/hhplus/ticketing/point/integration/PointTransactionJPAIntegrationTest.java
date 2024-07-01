package hhplus.ticketing.point.integration;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.user.components.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class PointTransactionJPAIntegrationTest {
    @Autowired
    PointService pointService;
    @Autowired
    UserService userService;

    private User setUser(long userId, long balance){
        return new User(userId, balance);
    }



    @Test
    @DisplayName("포인트를 충전하면, 유저 잔액이 충전 포인트만큼 늘어난다.")
    void recharge_point(){
        long pointAmount = 10000;
        User user = setUser(1, 0);
        pointService.rechargePoint(user, pointAmount);
        userService.save(user);


        User userFound = userService.findById(user.getUserId());


        assertThat(userFound.getBalance()).isEqualTo(pointAmount);

    }

    @Test
    @DisplayName("포인트를 사용한다.")
    void use_point(){
        //given
        long pointAmount = 10000;
        long usePoint = 5000;

        User user = setUser(1, pointAmount);
        //when
        pointService.usePoint(user, usePoint);
        userService.save(user);
        //then
        User userFound = userService.findById(user.getUserId());
        assertThat(userFound.getBalance()).isEqualTo(pointAmount-usePoint);
    }

    @Test
    @DisplayName("포인트가 부족할 경우 에러가 발생한다.")
    void cannot_use_point_larger_than_balance(){
        long balance = 1000;
        long usePoint = 3000;

        User user = setUser(1, balance);
        userService.save(user);



        assertThrows(IllegalArgumentException.class,
                () -> pointService.usePoint(user, usePoint));
    }

    @Test
    @DisplayName("포인트 충전/사용 내역이 기록된다.")
    void record_point_transaction_history(){

        long userId = 1;

        User user = setUser(userId, 0);
        long rechargePoint = 50000;
        long usePoint = 30000;

        pointService.rechargePoint(user, rechargePoint);
        pointService.usePoint(user, usePoint);
        userService.save(user);

        List<PointTransaction> pointTransactionList = pointService.queryTransactions(userId);
        assertThat(pointTransactionList).hasSize(2);
        assertThat(pointTransactionList.get(0).amount()).isEqualTo(rechargePoint);
    }

}
