package hhplus.ticketing.domain.point.repository;

import hhplus.ticketing.domain.point.models.PointTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PointRepository {
    PointTransaction savePointTransaction(PointTransaction pointTransaction);
    List<PointTransaction> getAllPointTransactionByUserId(long userId);
}
