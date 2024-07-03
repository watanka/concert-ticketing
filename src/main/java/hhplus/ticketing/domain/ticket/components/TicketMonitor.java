package hhplus.ticketing.domain.ticket.components;

import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketMonitor {

    @Autowired
    private final TicketRepository ticketRepository;

    private final long expireInterval = 5000;

    @Scheduled(fixedDelay=expireInterval)
    public void run(){
        List<Ticket> pendingTickets = findAllPendingTickets();
        LocalDateTime now = LocalDateTime.now();

        for (Ticket pendingTicket : pendingTickets) {
            cancelPendingTickets(pendingTicket, now);
        }
    }


    public void cancelPendingTickets(Ticket ticket, LocalDateTime now){
        if (isTimePassed(ticket.getReservedTime(), now, expireInterval)) {
            ticket.updateStatus(TicketStatus.CANCELLED);
            ticketRepository.save(ticket);
        }
    }

    public List<Ticket> findAllPendingTickets(){
        return ticketRepository.findAllPendingTicket();
    }


    private boolean isTimePassed(LocalDateTime startTime, LocalDateTime timeNow, long interval){
        return startTime.plusMinutes(interval).isAfter(timeNow);
    }

}
