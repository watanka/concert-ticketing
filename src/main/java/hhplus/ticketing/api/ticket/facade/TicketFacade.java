package hhplus.ticketing.api.ticket.facade;

import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.ticket.components.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketFacade {
    private final TicketService ticketService;
    private final ConcertReader concertReader;

    public void register(long userId, long price, long concertId, LocalDateTime showTime, long seatNo) {
        LocalDateTime now = LocalDateTime.now();
        Seat seat = concertReader.getSeat(concertId, showTime, seatNo);
        ticketService.register(userId, price, seat, now);
    }
}
