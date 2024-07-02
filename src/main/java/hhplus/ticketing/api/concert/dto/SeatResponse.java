package hhplus.ticketing.api.concert.dto;

import hhplus.ticketing.domain.concert.models.Seat;
import lombok.Builder;

@Builder
public record SeatResponse(long seatNo, long price, String status) {


    public static SeatResponse from(Seat seat){
        return SeatResponse.builder()
                .seatNo(seat.getSeatNo())
                .status(seat.getStatus().toString())
                .build();

    }
}
