package hhplus.ticketing.domain.ticket.infra;

import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

public class MemoryTicketRepository implements TicketRepository {

    Map<Long, Ticket> tickets = new HashMap<>();

    @Override
    public Ticket findByUserId(long userId) {
        return tickets.get(userId);
    }

    @Override
    public Ticket findById(long ticketId) {
        return null;
    }

    @Override
    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getUserId(), ticket);
        return ticket;
    }


}
