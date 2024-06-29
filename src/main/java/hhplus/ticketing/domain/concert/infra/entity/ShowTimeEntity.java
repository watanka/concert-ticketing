package hhplus.ticketing.domain.concert.infra.entity;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class ShowTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="concert_id")
    private long concertId;

    @Column(name="time")
    private LocalDateTime time;

    @Column(name="concert_hall")
    private ConcertHall concertHall;

    public ShowTimeEntity(long concertId, LocalDateTime time, ConcertHall concertHall) {
        this.concertId = concertId;
        this.time = time;
        this.concertHall = concertHall;
    }

}
