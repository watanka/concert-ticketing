package hhplus.ticketing.domain.ticket.infra;

import hhplus.ticketing.domain.ticket.models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketJPARepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByUserId(long userId);
    TicketEntity findById(long id);

    List<TicketEntity> findAllByConcertIdAndShowTime(long concertId, LocalDateTime showTime);


    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ticket t " +
            "WHERE t.concertId = :concertId AND t.showTime = :showTime " +
            "AND t.seatNo = :seatNo")
    boolean existsByConcertIdAndShowTimeAndSeatNo(@Param("concertId") long concertId,
                                                  @Param("showTime") LocalDateTime showTime,
                                                  @Param("seatNo") long seatNo);

    List<TicketEntity> findAllByStatus(TicketStatus status);

    List<TicketEntity> findAllByUserId(long userId);
}
