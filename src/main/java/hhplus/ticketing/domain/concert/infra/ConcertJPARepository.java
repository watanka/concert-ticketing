package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertJPARepository extends JpaRepository<ConcertEntity, Long> {

    ConcertEntity save(ConcertEntity concert);
    ConcertEntity findConcertEntityById(long id);
    List<ConcertEntity> findAll();
}
