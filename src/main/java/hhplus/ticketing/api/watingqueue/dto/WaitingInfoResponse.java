package hhplus.ticketing.api.watingqueue.dto;

import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import lombok.Builder;

@Builder
public record WaitingInfoResponse(long userId, long waitingNo) {

    public static WaitingInfoResponse from(WaitingInfo waitingInfo){
        return WaitingInfoResponse.builder()
                .userId(waitingInfo.userId())
                .waitingNo(waitingInfo.waitingNo())
                .build();
    }
}
