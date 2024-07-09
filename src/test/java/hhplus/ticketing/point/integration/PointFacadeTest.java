package hhplus.ticketing.point.integration;

import hhplus.ticketing.api.point.facade.PointFacade;
import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.infra.PointTransactionJPARepository;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.infra.UserJPARepository;
import hhplus.ticketing.domain.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PointFacadeTest {
    @Autowired
    PointFacade pointFacade;

    @Autowired
    PointService pointService;
    @Autowired
    UserService userService;

    @Autowired
    PointTransactionJPARepository pointTransactionJPARepository;

    @Autowired
    UserJPARepository userJPARepository;

    @BeforeEach
    void setUp(){
        pointTransactionJPARepository.deleteAll();
        userJPARepository.deleteAll();
    }

    private User setUser(long userId, long balance){
        return new User(userId, balance);
    }



    @Test
    @DisplayName("포인트를 충전하면, 유저 잔액이 충전 포인트만큼 늘어난다.")
    void recharge_point(){
        long pointAmount = 10000;
        Point rechargePoint = new Point(pointAmount, PointType.RECHARGE);
        User user = setUser(1, 0);
        userService.save(user);


        pointFacade.transact(user.getId(), rechargePoint);


        User userFound = userService.findById(user.getId());


        assertThat(userFound.getBalance()).isEqualTo(pointAmount);

    }

    @Test
    @DisplayName("포인트를 사용한다.")
    void use_point(){
        //given
        long pointAmount = 10000;
        long usePointAmount = 5000;

        Point usePoint = new Point(usePointAmount, PointType.USE);
        User user = setUser(1, pointAmount);
        userService.save(user);
        //when
        pointFacade.transact(user.getId(), usePoint);
        //then
        User userFound = userService.findById(user.getId());
        assertThat(userFound.getBalance()).isEqualTo(pointAmount-usePointAmount);
    }

    @Test
    @DisplayName("포인트가 부족할 경우 에러가 발생한다.")
    void cannot_use_point_larger_than_balance(){
        long balance = 1000;
        long usePointAmount = 3000;

        Point usePoint = new Point(usePointAmount, PointType.USE);
        User user = setUser(1, balance);
        userService.save(user);



        assertThrows(InsufficientBalanceException.class,
                () -> pointFacade.transact(user.getId(), usePoint));
    }

    @Test
    @DisplayName("포인트 충전/사용 내역이 기록된다.")
    void record_point_transaction_history(){

        long userId = 1;

        User user = setUser(userId, 0);
        long rechargePointAmount = 50000;
        long usePointAmount = 30000;
        userService.save(user);

        Point rechargePoint = new Point(rechargePointAmount, PointType.RECHARGE);
        Point usePoint = new Point(usePointAmount, PointType.USE);

        pointFacade.transact(user.getId(), rechargePoint);
        pointFacade.transact(user.getId(), usePoint);

        List<PointTransaction> pointTransactionList = pointService.queryTransactions(userId);
        assertThat(pointTransactionList).hasSize(2);
        assertThat(pointTransactionList.get(0).getAmount()).isEqualTo(rechargePoint.getAmount());
    }
}
