package hhplus.ticketing.ticket.unit;

import java.util.HashMap;
import java.util.Map;

public class MemoryTicketRepository implements TicketRepository {

    Map<Long, Ticket> tickets = new HashMap<>();

    @Override
    public Ticket findById(long userId) {
        return tickets.get(userId);
    }

    @Override
    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getUserId(), ticket);
        return ticket;
    }


}
