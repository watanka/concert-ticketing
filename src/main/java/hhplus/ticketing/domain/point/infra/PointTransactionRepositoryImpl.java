package hhplus.ticketing.domain.point.infra;


import hhplus.ticketing.domain.point.models.PointTransaction;
import hhplus.ticketing.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointTransactionRepositoryImpl implements PointRepository {
    @Autowired
    private final PointTransactionJPARepository pointTransactionJPARepository;

    @Override
    public PointTransaction savePointTransaction(PointTransaction pointTransaction) {
        PointTransactionEntity pointTransactionEntity = pointTransactionJPARepository.save(PointTransactionEntity.from(pointTransaction));
        return PointTransactionEntity.to(pointTransactionEntity);
    }

    @Override
    public List<PointTransaction> getAllPointTransactionByUserId(long userId) {
        List<PointTransactionEntity> pointTransactionEntity = pointTransactionJPARepository.findAllByUserId(userId);
        return pointTransactionEntity.stream()
                .map(PointTransactionEntity::to)
                .toList();
    }
}
