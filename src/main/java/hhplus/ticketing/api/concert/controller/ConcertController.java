package hhplus.ticketing.api.concert.controller;

import hhplus.ticketing.api.concert.dto.ConcertResponse;
import hhplus.ticketing.domain.concert.models.Concert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConcertController {

    @GetMapping("/concerts")
    ResponseEntity<ConcertResponse> getConcertList(){

        ConcertResponse response = new ConcertResponse(List.of(new Concert("뉴진스 단독 콘서트", "뉴진스")));

        return ResponseEntity.ok(response);
    }
}
