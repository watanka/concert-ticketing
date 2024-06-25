package hhplus.ticketing.domain.concert.models;

public class Seat {
    long seatNo;
    long concertId;
    long showTimeId;
    SeatStatus status;


    public Seat(long seatNo, long concertId, long showTimeId, SeatStatus status) {
        this.seatNo = seatNo;
        this.concertId = concertId;
        this.showTimeId = showTimeId;
        this.status = status;
    }

    public long getSeatNo() {
        return seatNo;
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
