package hhplus.ticketing.point.integration;

import hhplus.ticketing.api.point.facade.PointFacade;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.infra.PointTransactionJPARepository;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.infra.UserJPARepository;
import hhplus.ticketing.domain.user.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PointTransactionConcurrencyTest {

    @Autowired
    PointService pointService;
    @Autowired
    UserService userService;

    @Autowired
    PointFacade pointFacade;

    @Autowired
    PointTransactionJPARepository pointTransactionJPARepository;

    @Autowired
    UserJPARepository userJPARepository;

    @AfterEach
    void setUp(){
        pointTransactionJPARepository.deleteAll();
        userJPARepository.deleteAll();
    }


    @Test
    @Disabled
    @DisplayName("포인트가 동시에 처리되는 경우를 확인한다.")
    void concurrent_request_handled_by_order() throws InterruptedException {
        User user = new User(1, 0);
        userService.save(user);

        final int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(() ->{
                    Point rechargePoint = new Point(100, PointType.RECHARGE);
                    pointFacade.transact(user, rechargePoint);

                    latch.countDown();
            });
        }

        latch.await();

        User userFound = userService.findById(user.getId());

        List<PointTransaction> pointTransactionList = pointService.queryTransactions(user.getId());
        for (PointTransaction p: pointTransactionList) {
            System.out.println("transaction id: " + p.id() + "\namount: " + p.amount() + "\ntime: " + p.transactionTime());
        }

        assertThat(userFound.getBalance()).isEqualTo(100 * numThreads);

    }


    @Test
    @DisplayName("유저 정보가 올바르게 저장되는지 확인")
    void check_user_info_correctly_saved(){
        User user = new User(1, 0);
        userService.save(user);

        Point point1 = new Point(1000, PointType.RECHARGE);
        userService.updateBalance(user, point1);


    }
}
