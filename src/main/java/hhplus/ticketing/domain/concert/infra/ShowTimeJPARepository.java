package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.ShowTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowTimeJPARepository extends JpaRepository<ShowTimeEntity, Long> {

    ShowTimeEntity save(ShowTimeEntity showTime);
    List<ShowTimeEntity> findShowTimeEntityListByConcertId(long concertId);
    List<ShowTimeEntity> findAll();

}
