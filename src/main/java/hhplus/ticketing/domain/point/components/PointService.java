package hhplus.ticketing.domain.point.components;

import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    @Autowired
    private final PointRepository pointRepository;

    public PointTransaction recordPointTransaction(long userId, Point point, LocalDateTime time) {
        PointTransaction pointTransaction = new PointTransaction(userId, time, point.getAmount(), point.getType());
        return pointRepository.savePointTransaction(pointTransaction);
    }

    public List<PointTransaction> queryTransactions(long userId) {
        return pointRepository.getAllPointTransactionByUserId(userId);
    }
}
