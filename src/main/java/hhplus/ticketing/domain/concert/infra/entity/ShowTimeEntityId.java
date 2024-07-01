package hhplus.ticketing.domain.concert.infra.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShowTimeEntityId {
    @Column(name="concert_id")
    private long concertId;

    @Column(name="time")
    private LocalDateTime time;
}