package hhplus.ticketing.domain.ticket.infra;

import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @Autowired
    TicketJPARepository ticketJPARepository;

    @Override
    public Ticket findByUserId(long userId) {
        TicketEntity ticketEntity = ticketJPARepository.findByUserId(userId);

        return TicketEntity.to(ticketEntity);
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
}
