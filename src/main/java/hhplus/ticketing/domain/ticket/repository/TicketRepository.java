package hhplus.ticketing.domain.ticket.repository;

import hhplus.ticketing.domain.ticket.models.Ticket;

public interface TicketRepository {
    Ticket findById(long userId);

    Ticket save(Ticket ticket);
}
