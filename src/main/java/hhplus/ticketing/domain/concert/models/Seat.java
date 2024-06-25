package hhplus.ticketing.domain.concert.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Seat {
    long seatNo;
    long concertId;
    SeatStatus status;

    public boolean isAvailable(){
        return this.status == SeatStatus.AVAILABLE;
    }

    public void updateStatus(SeatStatus status){
        this.status = status;
    }
}
