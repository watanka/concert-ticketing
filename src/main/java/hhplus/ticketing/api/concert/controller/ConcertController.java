package hhplus.ticketing.api.concert.controller;

import hhplus.ticketing.api.concert.dto.SeatListResponse;
import hhplus.ticketing.api.concert.dto.ShowTimeListResponse;
import hhplus.ticketing.api.concert.dto.ConcertResponse;
import hhplus.ticketing.domain.concert.models.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ConcertController {

    @GetMapping("/concerts")
    ConcertResponse getConcertList(@RequestHeader(name="Authorization") String token){

        return new ConcertResponse(List.of(new Concert("뉴진스 단독 콘서트", "뉴진스")));
    }

    @GetMapping("/concerts/{concertId}")
    ShowTimeListResponse getShowTimeList(@RequestHeader(name="Authorization") String token,
                                         @PathVariable(name="concertId") long concertId){
        LocalDateTime time = LocalDateTime.of(2024, 6,26,15,0);

        return ShowTimeListResponse.from(List.of(new ShowTime(1, time, ConcertHall.JAMSIL)));
    }

    @GetMapping("/concerts/{concertId}/{showTimeId}")
    SeatListResponse getSeatList(@RequestHeader(name="Authorization") String token,
                                 @PathVariable(name="concertId") long concertId,
                                 @PathVariable(name="showTimeId") long showTimeId){
        LocalDateTime time = LocalDateTime.of(2024, 6,26,15,0);
        return SeatListResponse.from(List.of(
                new Seat(1, "아이유 10주년 콘서트", ConcertHall.JAMSIL, time, 300000, SeatStatus.AVAILABLE),
                new Seat(1, "뉴진스 단독 콘서트", ConcertHall.LOTTE_TOWER, time, 200000, SeatStatus.AVAILABLE)
                ));

    }


}
