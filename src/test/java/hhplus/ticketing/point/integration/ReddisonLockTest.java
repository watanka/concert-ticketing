package hhplus.ticketing.point.integration;

import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReddisonLockTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    void test_redisson_lock() throws InterruptedException {
        final int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        AtomicInteger numCatch = new AtomicInteger();;
        {
            for (int i = 0; i < numThreads; i++) {
                executorService.execute(() ->{
                    if (catchLock()){
                        numCatch.getAndIncrement();
                    }

                    latch.countDown();
                });
            }
        }
        latch.await();
        System.out.println(numCatch.get());



    }

    private boolean catchLock() {
        RLock rLock = redissonClient.getLock("random-key");
        try{
            boolean available = rLock.tryLock(3, 3, TimeUnit.SECONDS);
            return available;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
