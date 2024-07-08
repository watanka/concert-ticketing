package hhplus.ticketing.domain.concert.infra.entity;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.ShowTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name="showtime")
@NoArgsConstructor
@Getter
@IdClass(ShowTimeEntityId.class)
public class ShowTimeEntity {

    @Id
    @Column(name="concert_id")
    private long concertId;

    @Id
    @Column(name="time")
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    @Column(name="concert_hall")
    private ConcertHall concertHall;



    @Builder
    public ShowTimeEntity(long concertId, LocalDateTime time, ConcertHall concertHall) {
        this.concertId = concertId;
        this.time = time;
        this.concertHall = concertHall;
    }

    public static ShowTime to(ShowTimeEntity showTimeEntity) {
        return ShowTime.builder()
                .concertId(showTimeEntity.getConcertId())
                .time(showTimeEntity.getTime())
                .concertHall(showTimeEntity.getConcertHall())
                .build();
    }

    public static ShowTimeEntity from(ShowTime showTime) {
        return ShowTimeEntity.builder()
                .concertId(showTime.getConcertId())
                .time(showTime.getTime())
                .concertHall(showTime.getConcertHall())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowTimeEntity that = (ShowTimeEntity) o;
        return concertId == that.concertId && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertId, time);
    }
}
