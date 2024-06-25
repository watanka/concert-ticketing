package hhplus.ticketing.ticket.unit;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.point.models.User;

import java.time.LocalDateTime;

public class Ticket {
    Seat seat;
    User user;


    public Ticket( Seat seat, User user) {
        this.seat = seat;
        this.user = user;
    }

    public LocalDateTime getDateInfo(){
        return seat.getShowTime();
    }


    public long getSeatNo() {
        return seat.getSeatNo();
    }

    public ConcertHall getConcertHall() {
        return seat.getConcertHall();
    }

    public long getUserId() {
        return user.getUserId();
    }
}
