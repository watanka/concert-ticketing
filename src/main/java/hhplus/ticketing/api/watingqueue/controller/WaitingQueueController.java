package hhplus.ticketing.api.watingqueue.controller;

import hhplus.ticketing.api.watingqueue.dto.TokenRequest;
import hhplus.ticketing.api.watingqueue.dto.TokenResponse;
import hhplus.ticketing.api.watingqueue.dto.WaitingInfoResponse;
import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.components.WaitingQueueService;
import hhplus.ticketing.domain.token.models.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Tag(name="대기열", description="콘서트 예약 입장전 대기열")
@RequiredArgsConstructor
@RestController
public class WaitingQueueController {

    @Autowired
    private final WaitingQueueService waitingQueueService;

    @Operation(summary="대기열 등록", description="사용자를 대기열에 등록하고, 토큰을 부여합니다.")
    @PostMapping("/tokens")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Token.class)))
    public TokenResponse registerWaitingQueue(@RequestBody TokenRequest tokenRequest){
        LocalDateTime registerTime = LocalDateTime.now();
        return TokenResponse.from(waitingQueueService.register(tokenRequest.concertId(), tokenRequest.userId(), registerTime));
    }

    @Operation(summary="토큰 조회", description="토큰 조회를 통해 현재 대기열 순번을 조회합니다.")
    @GetMapping(value="/tokens")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WaitingInfoResponse.class)))
    public WaitingInfoResponse queryWaitingNum( @RequestBody long concertId, @RequestHeader(name="Authorization") String jwt) throws ResponseStatusException {

        try{
            Token token = new Token(concertId, jwt);
            return WaitingInfoResponse.from(waitingQueueService.query(token));
        } catch (InvalidTokenException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token", e);
        }


    }
}
