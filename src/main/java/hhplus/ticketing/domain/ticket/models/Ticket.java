package hhplus.ticketing.domain.ticket.models;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.point.models.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ticket {
    Seat seat;
    User user;
    TicketStatus status;
    LocalDateTime reservedTime;


    public Ticket( Seat seat, User user) {
        this.seat = seat;
        this.user = user;
        this.reservedTime = LocalDateTime.now();
        this.status = TicketStatus.PENDING;
    }

    public LocalDateTime getDateInfo(){
        return seat.getShowTime();
    }

    public long getUserId(){
        return user.getUserId();
    }

    public long getSeatNo(){
        return seat.getSeatNo();
    }

    public ConcertHall getConcertHall(){
        return seat.getConcertHall();
    }

    public void updateStatus(TicketStatus status){
        this.status = status;
    }

}
