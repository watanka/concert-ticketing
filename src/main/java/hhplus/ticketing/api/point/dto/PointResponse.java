package hhplus.ticketing.api.point.dto;

import hhplus.ticketing.domain.point.models.PointTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public record PointResponse(
        long balance,
        List<PointTransaction> pointHistory
) {

}
