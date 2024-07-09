package hhplus.ticketing.domain.point.infra;

import com.github.ansell.jdefaultdict.JDefaultDict;
import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.repository.PointRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

public class MemoryPointRepository implements PointRepository {

    ConcurrentMap<Long, List<PointTransaction>> pointHistoryMap = new JDefaultDict<>(k -> new ArrayList<>());


    public PointTransaction savePointTransaction(PointTransaction pointTransaction) {
        List<PointTransaction> transactionHistory = pointHistoryMap.get(pointTransaction.getUserId());
        transactionHistory.add(pointTransaction);
        return pointTransaction;

    }

    @Override
    public List<PointTransaction> getAllPointTransactionByUserId(long userId) {
        return pointHistoryMap.getOrDefault(userId, null);
    }
}
