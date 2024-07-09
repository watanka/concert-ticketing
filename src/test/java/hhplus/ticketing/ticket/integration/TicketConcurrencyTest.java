package hhplus.ticketing.ticket.integration;

import hhplus.ticketing.api.ticket.facade.TicketFacade;
import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.infra.TicketEntity;
import hhplus.ticketing.domain.ticket.infra.TicketJPARepository;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TicketConcurrencyTest {

    @Autowired
    TicketJPARepository ticketJPARepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketFacade ticketFacade;

    Seat seat = Seat.builder()
                    .seatNo(1)
                    .concertId(1)
                    .concertName("아이유 10주년 콘서트")
                    .concertHall(ConcertHall.JAMSIL)
                    .showTime(LocalDateTime.now())
                    .status(SeatStatus.AVAILABLE)
                    .build();

    @Test
    @DisplayName("한 좌석을 동시에 예약요청 시, 하나의 요청만 성공한다.")
    void only_one_request_success_when_concurrent_requests_on_a_seat() throws InterruptedException {
        LocalDateTime showTime = LocalDateTime.of(2024, 7, 13, 15, 0);

        AtomicInteger successCnt = new AtomicInteger();
        AtomicInteger failCnt = new AtomicInteger();

        final int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(() ->{
                try{
                    ticketService.register(1, 10000, seat, LocalDateTime.now());
                    successCnt.getAndIncrement();
                } catch (Exception e) {
                    failCnt.getAndIncrement();
                }

                latch.countDown();
            });
        }
        latch.await();

        assertThat(ticketService.isReserved(seat)).isTrue();
        assertThat(successCnt.get()).isEqualTo(1);
        assertThat(failCnt.get()).isEqualTo(numThreads - 1);
    }
}
