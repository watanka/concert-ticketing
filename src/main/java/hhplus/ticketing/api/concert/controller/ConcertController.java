package hhplus.ticketing.api.concert.controller;
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
    private final ConcertReader concertReader;

    @Operation(summary="콘서트 조회", description="현재 진행중인 콘서트 리스트를 조회합니다.")
    @GetMapping("/concerts")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = Concert.class))))
    List<Concert> getConcertList(@RequestHeader(name="Authorization") String token){

        return concertReader.getConcertList();

    }

    @Operation(summary="공연시간 조회", description="콘서트 공연시간 리스트를 조회합니다.")
    @GetMapping("/concerts/{concertId}")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = ShowTime.class))))
    List<ShowTime> getShowTimeList(@RequestHeader(name="Authorization") String token,
                                   @PathVariable(name="concertId") long concertId){

        return concertReader.getShowTimeList(concertId);
    }

    @Operation(summary="좌석 조회", description="공연시간의 좌석 리스트를 조회합니다.")
    @GetMapping("/concerts/{concertId}/{showTime}")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = Seat.class))))
    List<Seat> getSeatList(@RequestHeader(name="Authorization") String token,
                           @PathVariable(name="concertId") long concertId,
                           @PathVariable(name="showTime") String showTime){

        return concertReader.getSeatList(concertId, LocalDateTime.parse(showTime));


    }


}
