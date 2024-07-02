package hhplus.ticketing.domain.ticket.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketJPARepository extends JpaRepository<TicketEntity, Long> {

    TicketEntity findByUserId(long userId);
    TicketEntity findById(long id);

    List<TicketEntity> findByConcertIdAndShowTime(long concertId, LocalDateTime showTime);
}
