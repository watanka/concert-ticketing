package hhplus.ticketing.domain.point.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointTransactionJPARepository extends JpaRepository<PointTransactionEntity, Long> {
    PointTransactionEntity save(PointTransactionEntity pointTransactionEntity);
    List<PointTransactionEntity> findAllByUserId(long userId);
}
