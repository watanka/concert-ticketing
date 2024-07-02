package hhplus.ticketing.domain.ticket.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJPARepository extends JpaRepository<TicketEntity, Long> {

    TicketEntity findByUserId(long userId);
    TicketEntity findById(long id);
}
