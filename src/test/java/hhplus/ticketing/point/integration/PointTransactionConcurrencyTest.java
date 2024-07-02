package hhplus.ticketing.point.integration;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.infra.PointTransactionJPARepository;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.infra.UserJPARepository;
import hhplus.ticketing.domain.user.models.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointTransactionConcurrencyTest {

    @Autowired
    PointService pointService;
    @Autowired
    UserService userService;

    @Autowired
    PointUseCase pointUseCase;

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
    @DisplayName("포인트가 동시에 처리되는 경우를 확인한다.")
    void concurrent_request_handled_by_order() throws InterruptedException {
        User user = new User(1, 0);
        userService.save(user);

        final int numThreads = 100;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        {
            for (int i = 0; i < numThreads; i++) {
                executorService.execute(() ->{
                        Point rechargePoint = new Point(100, PointType.RECHARGE);
                        pointUseCase.transact(user, rechargePoint);


                        latch.countDown();
                });
            }
        }
        latch.await();

        User userFound = userService.findById(user.getUserId());

        assertThat(userFound.getBalance()).isEqualTo(100 * numThreads);

    }
}
