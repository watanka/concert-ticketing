package hhplus.ticketing.domain.ticket.components;

import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketMonitor {

    private final long expInterval = 5000;

    @Scheduled(fixedDelay=expInterval)
    public void run(){
        checkPendingTickets();
    };
    public void checkPendingTickets(){

    };

    public void checkPendingTicket(Ticket ticket, LocalDateTime now){
        if (isTimePassed(ticket.getReservedTime(), now, expInterval)) {
            ticket.updateStatus(TicketStatus.CANCELLED);

        }
    }

    private boolean isTimePassed(LocalDateTime startTime, LocalDateTime timeNow, long interval){
        return startTime.plusMinutes(interval).isAfter(timeNow);
    }

}
