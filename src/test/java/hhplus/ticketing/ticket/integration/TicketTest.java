package hhplus.ticketing.ticket.integration;

import hhplus.ticketing.domain.ticket.components.TicketMonitor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TicketTest {

    @SpyBean
    private TicketMonitor ticketMonitor;

    @Test
    @Disabled
    @DisplayName("티켓에 대한 모니터링 매 5초마다 수행")
    void monitoring_on_ticket_every_5s() throws InterruptedException{
        await()
            .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> verify(ticketMonitor, atLeast(2)).run());
    }

}
