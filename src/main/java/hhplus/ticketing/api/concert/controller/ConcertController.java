package hhplus.ticketing.api.concert.controller;

import hhplus.ticketing.api.concert.dto.ConcertResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConcertController {

    @GetMapping("/concerts")
    ResponseEntity<ConcertResponse> getConcertList(){

        ConcertResponse response = new ConcertResponse(List.of());

        return ResponseEntity.ok(response);
    }
}
