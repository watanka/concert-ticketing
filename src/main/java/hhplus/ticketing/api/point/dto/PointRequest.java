package hhplus.ticketing.api.point.dto;

import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;

public record PointRequest(
        long userId,
        long point,
        String pointType) {

    public Point toPoint(){
        return new Point(point, PointType.valueOf(pointType));
    }

}
