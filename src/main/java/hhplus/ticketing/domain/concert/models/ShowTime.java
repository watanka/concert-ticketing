package hhplus.ticketing.domain.concert.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ShowTime {
    long showTimeId;
    long concertId;
    LocalDateTime time;
    ConcertHall concertHall;

}
