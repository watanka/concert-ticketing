package hhplus.ticketing.api.point.dto;

public record PointRequest(
        long userId,
        long point,
        String pointType) {


}
