package hhplus.ticketing.point.integration;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    @DisplayName("포인트가 동시에 처리되는 경우를 확인한다.")
    void concurrent_request_handled_by_order(){
        User user = new User(1, 0);
        userService.save(user);

        final int numThreads = 100;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for(int i=0; i<numThreads;i++){
            executorService.execute(()->
                pointService.rechargePoint(user, 100)
                    );
        }
        userService.save(user);

        User userFound = userService.findById(user.getUserId());

        assertThat(userFound.getBalance()).isEqualTo(100 * numThreads);

    }
}
