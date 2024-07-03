package hhplus.ticketing.domain.ticket.repository;

import hhplus.ticketing.domain.ticket.models.Ticket;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository {
    Ticket findByUserId(long userId);

    List<Ticket> findByConcertIdAndShowTime(long concertId, LocalDateTime showTime);

    boolean existsByConcertIdAndShowTimeAndSeatNo(long concertId, LocalDateTime showTime, long seatNo);

    Ticket findById(long ticketId);
    Ticket save(Ticket ticket);

    List<Ticket> findAllPendingTicket();
}
