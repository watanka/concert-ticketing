package hhplus.ticketing.concert.integration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ConcertJPARepository extends JpaRepository<ConcertEntity, Long> {
}
