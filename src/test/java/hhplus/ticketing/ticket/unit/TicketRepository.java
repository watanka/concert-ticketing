package hhplus.ticketing.ticket.unit;

public interface TicketRepository {
    Ticket findById(long userId);

    Ticket save(Ticket ticket);
}
