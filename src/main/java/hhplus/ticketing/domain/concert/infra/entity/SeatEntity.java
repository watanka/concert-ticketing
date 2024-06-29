package hhplus.ticketing.domain.concert.infra.entity;


import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class SeatEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="seat_no")
    private long seatNo;

    @Column(name="showtime_id")
    private long showTimeId;

    @Column(name="concert_hall")
    private ConcertHall concertHall;

    @Column(name="time")
    private LocalDateTime time;

    @Column(name="price")
    private long price;

    @Column(name="status")
    private SeatStatus status;


    public SeatEntity(long seatNo, long showTimeId, ConcertHall concertHall, LocalDateTime time, long price, SeatStatus status) {
        this.seatNo = seatNo;
        this.showTimeId = showTimeId;
        this.concertHall = concertHall;
        this.time = time;
        this.price = price;
        this.status = status;
    }
}
