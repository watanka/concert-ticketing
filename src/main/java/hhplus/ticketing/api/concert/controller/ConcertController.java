package hhplus.ticketing.api.concert.controller;

import hhplus.ticketing.api.concert.dto.SeatListResponse;
import hhplus.ticketing.api.concert.dto.ShowTimeListResponse;
import hhplus.ticketing.api.concert.dto.ConcertResponse;
import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name="콘서트 조회", description="콘서트/공연시간/좌석을 조회합니다.")
@RestController
@RequiredArgsConstructor
public class ConcertController {

    @Autowired
    ConcertReader concertReader;

    @Operation(summary="콘서트 조회", description="현재 진행중인 콘서트 리스트를 조회합니다.")
    @GetMapping("/concerts")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = ConcertResponse.class))))
    List<Concert> getConcertList(@RequestHeader(name="Authorization") String token){
        return concertReader.getConcertList();
//        return List.of(new Concert(1, "뉴진스 단독 콘서트", "뉴진스"));

    }

    @Operation(summary="공연시간 조회", description="콘서트 공연시간 리스트를 조회합니다.")
    @GetMapping("/concerts/{concertId}")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = ShowTimeListResponse.class))))
    List<ShowTime> getShowTimeList(@RequestHeader(name="Authorization") String token,
                                         @PathVariable(name="concertId") long concertId){
        LocalDateTime time = LocalDateTime.of(2024, 6,26,15,0);

        return concertReader.getShowTimeList(concertId);
//        return List.of(new ShowTime(1, time, ConcertHall.JAMSIL));
    }

    @Operation(summary="좌석 조회", description="공연시간의 좌석 리스트를 조회합니다.")
    @GetMapping("/concerts/{concertId}/{showTime}")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = SeatListResponse.class))))
    List<Seat> getSeatList(@RequestHeader(name="Authorization") String token,
                                 @PathVariable(name="concertId") long concertId,
                                 @PathVariable(name="showTime") String showTime){
//        LocalDateTime time = LocalDateTime.of(2024, 6,26,15,0);

        return concertReader.getSeatList(concertId, LocalDateTime.parse(showTime));

//        return List.of(
//                new Seat(1, 1,  "아이유 10주년 콘서트", ConcertHall.JAMSIL, time, SeatStatus.AVAILABLE),
//                new Seat(1, 2, "뉴진스 단독 콘서트", ConcertHall.LOTTE_TOWER, time, SeatStatus.AVAILABLE)
//                );

    }


}
