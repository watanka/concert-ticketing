package hhplus.ticketing.domain.ticket.components;

import hhplus.ticketing.domain.ticket.infra.TicketRepositoryImpl;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketMonitor {

    @Autowired
    TicketRepository ticketRepository;

    private final long expInterval = 5000;

    @Scheduled(fixedDelay=expInterval)
    public void run(){
//        checkPendingTickets();
    };


    public void checkPendingTicket(Ticket ticket, LocalDateTime now){
        if (isTimePassed(ticket.getReservedTime(), now, expInterval)) {
            ticket.updateStatus(TicketStatus.CANCELLED);
            ticketRepository.save(ticket);

        }
    }

    private boolean isTimePassed(LocalDateTime startTime, LocalDateTime timeNow, long interval){
        return startTime.plusMinutes(interval).isAfter(timeNow);
    }

}
