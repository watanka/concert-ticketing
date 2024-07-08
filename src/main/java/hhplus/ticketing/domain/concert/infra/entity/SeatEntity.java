package hhplus.ticketing.domain.concert.infra.entity;


import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name="seat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@IdClass(SeatEntityId.class)
public class SeatEntity {

    @Id
    @Column(name="concert_id")
    private long concertId;

    @Id
    @Column(name="time")
    private LocalDateTime showTime;

    @Id
    @Column(name="seat_no")
    private long seatNo;

    @Column(name="concert_name")
    private String concertName;



    @Enumerated(EnumType.STRING)
    @Column(name="concert_hall")
    private ConcertHall concertHall;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private SeatStatus status;


    public static SeatEntity from(Seat seat) {
        return SeatEntity.builder()
                .concertId(seat.getConcertId())
                .showTime(seat.getShowTime())
                .seatNo(seat.getSeatNo())
                .concertHall(seat.getConcertHall())
                .showTime(seat.getShowTime())
                .status(seat.getStatus())
                .build();
    }

    public static Seat to(SeatEntity seat) {
        return Seat.builder()
                .seatNo(seat.getSeatNo())
                .concertId(seat.getConcertId())
                .concertName(seat.getConcertName())
                .concertHall(seat.getConcertHall())
                .showTime(seat.getShowTime())
                .status(seat.getStatus())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatEntity that = (SeatEntity) o;
        return concertId == that.concertId && seatNo == that.seatNo && Objects.equals(showTime, that.showTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertId, showTime, seatNo);
    }
}
