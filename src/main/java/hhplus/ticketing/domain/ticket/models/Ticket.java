package hhplus.ticketing.domain.ticket.models;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.user.models.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ticket {
    long id;
    Seat seat;
    User user;
    long price;
    TicketStatus status;
    LocalDateTime reservedTime;


    public Ticket( Seat seat, User user) {
        this.seat = seat;
        this.seat.updateStatus(SeatStatus.RESERVED);
        this.price = this.seat.getPrice();

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
