package hhplus.ticketing.ticket.integration;

import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.ticket.components.TicketMonitor;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TicketMonitorTest {

    @SpyBean
    private TicketMonitor ticketMonitor;

    @Autowired
    TicketService ticketService;


    @Test
    @DisplayName("티켓에 대한 모니터링 매 5초마다 수행")
    void monitoring_on_ticket_every_5s() throws InterruptedException{
        await()
            .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(ticketMonitor, atLeast(1)).run());
    }

    @Test
    @DisplayName("티켓 구매가능시간이 만료되면 티켓이 취소된다.")
    void cancel_ticket_when_reserved_time_expired(){
        long userId = 1;
        long price = 100000;
        Seat seat = Seat.builder()
                .seatNo(1)
                .concertId(1)
                .build();

        LocalDateTime time = LocalDateTime.now();
        Ticket ticket = ticketService.register(userId, price, seat, time.minusMinutes(5));
        ticketMonitor.cancelPendingTickets(ticket, time);

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.CANCELLED);

    }

}
