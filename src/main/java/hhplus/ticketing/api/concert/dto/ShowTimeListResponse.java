package hhplus.ticketing.api.concert.dto;

import hhplus.ticketing.domain.concert.models.ShowTime;

import java.io.Serializable;
import java.util.List;

public record ShowTimeListResponse(List<ShowTimeResponse> showTimeList) implements Serializable {

    public static ShowTimeListResponse from(List<ShowTime> showTimeList){
        return new ShowTimeListResponse(showTimeList.stream()
                .map(ShowTimeResponse::from)
                .toList());
    }
}
