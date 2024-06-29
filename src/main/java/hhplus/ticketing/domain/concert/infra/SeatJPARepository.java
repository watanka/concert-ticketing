package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatJPARepository extends JpaRepository<SeatEntity, Long> {
    SeatEntity save(SeatEntity seat);


    List<SeatEntity> getSeatListByShowTimeId(long showTimeId);
}
