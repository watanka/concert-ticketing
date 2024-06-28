package hhplus.ticketing.api.watingqueue.controller;

import hhplus.ticketing.api.watingqueue.dto.TokenRequest;
import hhplus.ticketing.api.watingqueue.dto.TokenResponse;
import hhplus.ticketing.api.watingqueue.dto.WaitingInfoResponse;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaitingQueueController {

    @PostMapping("/tokens")
    public TokenResponse registerWaitingQueue(@RequestBody TokenRequest tokenRequest){
        return new TokenResponse("TEMPORARY-TOKEN");
    }


    @GetMapping(value="/tokens",
            headers= HttpHeaders.AUTHORIZATION
    )
    public WaitingInfoResponse queryWaitingNum(@RequestHeader(name="Authorization") String token){

        return WaitingInfoResponse.from(new WaitingInfo(1, 10));
    }
}
