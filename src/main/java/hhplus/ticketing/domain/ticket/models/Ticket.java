package hhplus.ticketing.domain.ticket.models;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Ticket {
    long id;
    Seat seat;
    long userId;
    long price;
    TicketStatus status;
    LocalDateTime reservedTime;

    @Builder
    public Ticket(Seat seat, long price, long userId) {
        this.userId = userId;
        this.price = price;
        this.seat = seat;
        this.status = TicketStatus.PENDING;
        this.reservedTime = LocalDateTime.now();
    }

    public Ticket(Seat seat, long price, long userId, LocalDateTime reservedTime) {
        this.userId = userId;
        this.price = price;
        this.seat = seat;
        seat.updateStatus(SeatStatus.RESERVED);
        this.status = TicketStatus.PENDING;
        this.reservedTime = reservedTime;
    }

    public void updateStatus(TicketStatus status){
        this.status = status;
    }

    public long getConcertId(){
        return this.seat.getConcertId();
    }

    public LocalDateTime getShowTime() {
        return this.seat.getShowTime();
    }

    public long getSeatNo() {
        return seat.getSeatNo();
    }

    public ConcertHall getConcertHall() {
        return seat.getConcertHall();
    }
}
