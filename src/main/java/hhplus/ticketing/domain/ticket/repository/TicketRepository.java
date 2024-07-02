package hhplus.ticketing.domain.ticket.repository;

import hhplus.ticketing.domain.ticket.models.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository {
    Ticket findByUserId(long userId);
    Ticket findById(long ticketId);
    Ticket save(Ticket ticket);
}
