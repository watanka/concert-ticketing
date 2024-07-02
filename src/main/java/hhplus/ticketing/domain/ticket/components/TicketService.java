package hhplus.ticketing.domain.ticket.components;

import hhplus.ticketing.base.exceptions.UnavailableSeatException;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    @Autowired
    private final TicketRepository ticketRepository;

    public boolean isReserved(Seat seat){
        List<Ticket> ticketList = ticketRepository.findByConcertIdAndShowTime(seat.getConcertId(), seat.getShowTime());
        return ticketList.stream()
                .anyMatch(ticket -> ticket.getSeatNo() == seat.getSeatNo());
    }

    public Ticket register(long userId, long price, Seat seat) {
        if (isReserved(seat)){
            throw new UnavailableSeatException();
        }

        return ticketRepository.save(new Ticket(seat, price, userId));

    }
    public Ticket findByUserId(long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public Ticket findById(long ticketId){
        return ticketRepository.findById(ticketId);
    }

    public Ticket confirmPayment(Ticket ticket){
        ticket.updateStatus(TicketStatus.REGISTERED);
        ticketRepository.save(ticket);
        return ticket;
    }
}
