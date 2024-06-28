package hhplus.ticketing.api.concert.dto;

import hhplus.ticketing.domain.concert.models.Seat;

import java.util.List;

public record SeatListResponse(List<SeatResponse> seatList) {
    public static SeatListResponse from(List<Seat> seats){
        return new SeatListResponse(
                seats.stream()
                        .map(SeatResponse::from)
                        .toList()
        );
    }

}
