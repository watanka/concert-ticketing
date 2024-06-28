package hhplus.ticketing.domain.concert.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Seat {
    long seatNo;
    String concertName;
    ConcertHall concertHall;
    LocalDateTime showTime;
    long price;
    SeatStatus status;

    public boolean isAvailable(){
        return this.status == SeatStatus.AVAILABLE;
    }

    public void updateStatus(SeatStatus status){
        this.status = status;
    }

}
