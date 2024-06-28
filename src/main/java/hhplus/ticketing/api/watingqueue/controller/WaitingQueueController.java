package hhplus.ticketing.api.watingqueue.controller;

import hhplus.ticketing.api.watingqueue.dto.TokenRequest;
import hhplus.ticketing.api.watingqueue.dto.TokenResponse;
import hhplus.ticketing.api.watingqueue.dto.WaitingInfoResponse;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@Tag(name="대기열", description="콘서트 예약 입장전 대기열")
@RestController
public class WaitingQueueController {

    @Operation(summary="대기열 등록", description="사용자를 대기열에 등록하고, 토큰을 부여합니다.")
    @PostMapping("/tokens")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    public TokenResponse registerWaitingQueue(@RequestBody TokenRequest tokenRequest){
        return new TokenResponse("TEMPORARY-TOKEN");
    }

    @Operation(summary="토큰 조회", description="토큰 조회를 통해 현재 대기열 순번을 조회합니다.")
    @GetMapping(value="/tokens",
            headers= HttpHeaders.AUTHORIZATION
    )
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WaitingInfoResponse.class)))
    public WaitingInfoResponse queryWaitingNum(@RequestHeader(name="Authorization") String token){

        return WaitingInfoResponse.from(new WaitingInfo(1, 10));
    }
}
