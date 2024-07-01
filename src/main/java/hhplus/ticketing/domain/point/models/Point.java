package hhplus.ticketing.domain.point.models;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Point {
    public long amount;
    public PointType type;
}
