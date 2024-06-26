package hhplus.ticketing.domain.point.components;

import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.point.models.User;
import hhplus.ticketing.domain.point.repository.PointRepository;

import java.util.List;

public class PointService {

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    private PointRepository pointRepository;

    public PointTransaction rechargePoint(User user, long rechargeAmount) {
        PointTransaction pointTransaction = new PointTransaction(1, user.getUserId(), rechargeAmount, PointType.RECHARGE);
        user.rechargePoint(rechargeAmount);

        return pointRepository.savePointTransaction(pointTransaction);

    }

    public PointTransaction usePoint(User user, long useAmount) {
        PointTransaction pointTransaction = new PointTransaction(1, user.getUserId(), useAmount, PointType.USE);
        user.usePoint(useAmount);

        return pointRepository.savePointTransaction(pointTransaction);
    }

    public List<PointTransaction> queryTransactions(long userId) {
        return pointRepository.getAllPointTransactionByUserId(userId);
    }
}
