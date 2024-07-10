package hhplus.ticketing.api.watingqueue.dto;

import hhplus.ticketing.domain.token.models.WaitingInfo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WaitingInfoResponse(long waitingNo, LocalDateTime issuedAt) {

    public static WaitingInfoResponse from(WaitingInfo waitingInfo){
        return WaitingInfoResponse.builder()
                .waitingNo(waitingInfo.waitingNo())
                .issuedAt(waitingInfo.issuedAt())
                .build();
    }
}
