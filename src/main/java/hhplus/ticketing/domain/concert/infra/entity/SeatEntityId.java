package hhplus.ticketing.domain.concert.infra.entity;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
public class SeatEntityId {
    @Column(name="concert_id")
    private long concertId;

    @Column(name="time")
    private LocalDateTime showTime;

    @Column(name="seat_no")
    private long seatNo;




}
