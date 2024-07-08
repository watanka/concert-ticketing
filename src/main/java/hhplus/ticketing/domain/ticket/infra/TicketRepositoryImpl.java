package hhplus.ticketing.domain.ticket.infra;

import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    @Autowired
    private final TicketJPARepository ticketJPARepository;

    @Override
    public Ticket findByUserId(long userId) {
        TicketEntity ticketEntity = ticketJPARepository.findByUserId(userId);

        return TicketEntity.to(ticketEntity);
    }

    @Override
    public List<Ticket> findByConcertIdAndShowTime(long concertId, LocalDateTime showTime){
        List<TicketEntity> ticketEntityList = ticketJPARepository.findAllByConcertIdAndShowTime(concertId, showTime);

        return ticketEntityList.stream()
                .map(TicketEntity::to)
                .toList();
    }

    @Override
    public boolean existsByConcertIdAndShowTimeAndSeatNo(long concertId, LocalDateTime showTime, long seatNo){
        return ticketJPARepository.existsByConcertIdAndShowTimeAndSeatNo(concertId, showTime, seatNo);
    }

    @Override
    public Ticket findById(long ticketId) {
        TicketEntity ticketEntity = ticketJPARepository.findById(ticketId);

        return TicketEntity.to(ticketEntity);
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity ticketEntity = ticketJPARepository.save(TicketEntity.from(ticket));

        return TicketEntity.to(ticketEntity);
    }

    @Override
    public List<Ticket> findAllPendingTicket(){
        List<TicketEntity> ticketEntityList = ticketJPARepository.findAllByStatus(TicketStatus.PENDING);

        return ticketEntityList.stream()
                .map(TicketEntity::to).toList();
    }

    @Override
    public List<Ticket> findAllTicketbyUserId(long userId) {
        List<TicketEntity> ticketEntityList = ticketJPARepository.findAllByUserId(userId);
        return ticketEntityList.stream()
                .map(TicketEntity::to).toList();
    }
}
