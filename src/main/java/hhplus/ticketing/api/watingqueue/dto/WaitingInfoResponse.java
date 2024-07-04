package hhplus.ticketing.api.watingqueue.dto;

import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WaitingInfoResponse(long userId, long waitingNo, LocalDateTime issuedAt) {

    public static WaitingInfoResponse from(WaitingInfo waitingInfo){
        return WaitingInfoResponse.builder()
                .userId(waitingInfo.userId())
                .waitingNo(waitingInfo.waitingNo())
                .issuedAt(waitingInfo.issuedAt())
                .build();
    }
}
