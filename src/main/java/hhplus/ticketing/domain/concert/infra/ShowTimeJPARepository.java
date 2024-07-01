package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.ShowTimeEntity;
import hhplus.ticketing.domain.concert.infra.entity.ShowTimeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowTimeJPARepository extends JpaRepository<ShowTimeEntity, ShowTimeEntityId> {

    ShowTimeEntity save(ShowTimeEntity showTime);
    List<ShowTimeEntity> findShowTimeEntityListByConcertId(long concertId);
    List<ShowTimeEntity> findAll();

}
