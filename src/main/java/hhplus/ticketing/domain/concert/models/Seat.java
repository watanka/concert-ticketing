package hhplus.ticketing.domain.concert.models;

public class Seat {
    long seatId;
    long concertId;
    long showTimeId;
    SeatStatus status;


    public Seat(long seatId, long concertId, long showTimeId, SeatStatus status) {
        this.seatId = seatId;
        this.concertId = concertId;
        this.showTimeId = showTimeId;
        this.status = status;
    }

    public long getSeatId() {
        return seatId;
    }

    public long getConcertId() {
        return concertId;
    }

    public long getShowTimeId() {
        return showTimeId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void updateStatus(SeatStatus status){
        this.status = status;
    }
}
