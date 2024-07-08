package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.SeatEntity;
import hhplus.ticketing.domain.concert.infra.entity.SeatEntityId;
import hhplus.ticketing.domain.concert.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatJPARepository extends JpaRepository<SeatEntity, SeatEntityId> {
    SeatEntity save(SeatEntity seat);


    List<SeatEntity> getSeatListByConcertIdAndShowTime(long concertId, LocalDateTime showTime);

    SeatEntity getSeatByConcertIdAndShowTimeAndSeatNo(long concertId, LocalDateTime showTime, long seatNo);
}
