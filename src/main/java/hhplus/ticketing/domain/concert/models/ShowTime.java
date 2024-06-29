package hhplus.ticketing.domain.concert.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class ShowTime {
    long concertId;
    LocalDateTime time;
    ConcertHall concertHall;

}
