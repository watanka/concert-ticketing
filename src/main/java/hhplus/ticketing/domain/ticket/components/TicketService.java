package hhplus.ticketing.domain.ticket.components;

import hhplus.ticketing.api.point.facade.DistributedLock;
import hhplus.ticketing.base.exceptions.UnavailableSeatException;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    @Autowired
    private final TicketRepository ticketRepository;


    public boolean isReserved(Seat seat){
        return !seat.isAvailable();
//        return ticketRepository
//                .existsByConcertIdAndShowTimeAndSeatNo(seat.getConcertId(), seat.getShowTime(), seat.getSeatNo());
    }

    @DistributedLock(key="seatNo-.concat(seat.getSeatNo())")
    public Ticket register(long userId, long price, Seat seat, LocalDateTime reservedTime) {
        if(isReserved(seat)){
            throw new UnavailableSeatException();
        }
        seat.updateStatus(SeatStatus.RESERVED);
        Ticket ticket = new Ticket(seat, price, userId, reservedTime);
        return ticketRepository.save(ticket);

    }
    public List<Ticket> findByUserId(long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public Ticket findById(long ticketId){
        return ticketRepository.findById(ticketId);
    }

    public Ticket confirmPayment(long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId);
        ticket.updateStatus(TicketStatus.REGISTERED);
        ticketRepository.save(ticket);
        return ticket;
    }

    public List<Ticket> getTicketList(long userId){
        return ticketRepository.findAllTicketbyUserId(userId);
    }
}
