package hhplus.ticketing.api.point.dto;

import hhplus.ticketing.domain.point.models.PointTransaction;

import java.util.List;

public record PointHistoryResponse(
        long balance,
        List<PointTransaction> pointHistory
) {

}
