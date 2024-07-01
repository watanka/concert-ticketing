package hhplus.ticketing.domain.ticket.components;

import hhplus.ticketing.base.exceptions.UnavailableSeatException;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket register(User user, Seat seat) {
        if (!seat.isAvailable()){
            throw new UnavailableSeatException();
        }
        return ticketRepository.save(new Ticket(seat, user));

    }
    public Ticket query(long userId) {
        return ticketRepository.findById(userId);
    }

    public Ticket confirmPayment(Ticket ticket){
        ticket.updateStatus(TicketStatus.REGISTERED);
        return ticket;
    }
}
