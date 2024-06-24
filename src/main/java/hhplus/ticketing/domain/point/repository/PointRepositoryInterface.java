package hhplus.ticketing.domain.point.repository;

import hhplus.ticketing.domain.point.models.PointTransaction;

import java.util.List;

public interface PointRepositoryInterface {
    public PointTransaction savePointTransaction(PointTransaction pointTransaction);
    public List<PointTransaction> getAllPointTransactionByUserId(long userId);
}
