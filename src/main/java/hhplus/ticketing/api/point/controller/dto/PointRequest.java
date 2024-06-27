package hhplus.ticketing.api.point.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointRequest {
    long userId;
    long point;
    String pointType;

}
