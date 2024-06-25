package hhplus.ticketing.ticket.unit;

import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.point.models.User;

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket register(User user, Seat seat) {
        if (!seat.isAvailable()){
            throw new UnAvailableSeatException();
        }

        return ticketRepository.save(new Ticket(seat, user));


    }

    public Ticket query(long userId) {
        return ticketRepository.findById(userId);
    }
}
