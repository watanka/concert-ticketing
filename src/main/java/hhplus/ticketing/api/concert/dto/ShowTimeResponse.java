package hhplus.ticketing.api.concert.dto;

import hhplus.ticketing.domain.concert.models.ShowTime;
import lombok.Builder;

@Builder
public record ShowTimeResponse(long concertId, String time, String concertHall) {

    public static ShowTimeResponse from(ShowTime showTime){
        return ShowTimeResponse.builder()
                .concertId(showTime.getConcertId())
                .time(showTime.getTime().toString())
                .concertHall(showTime.getConcertHall().toString())
                .build();


    }

}
