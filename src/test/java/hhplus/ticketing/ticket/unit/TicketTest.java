package hhplus.ticketing.ticket.unit;

import hhplus.ticketing.domain.ticket.components.TicketMonitor;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.infra.MemoryTicketRepository;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.base.exceptions.UnAvailableSeatException;
import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.point.models.User;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class TicketTest {

    TicketRepository ticketRepository;
    TicketService ticketService;
    TicketMonitor ticketMonitor;

    LocalDateTime ticketDate = LocalDateTime.of(2024, 3, 5, 17, 5);
    ConcertHall concertHall = ConcertHall.LOTTE_TOWER;
    long seatNo = 1;
    User user;
    Seat seat;


    private Seat setSeat(SeatStatus status){
        return new Seat(seatNo, concertHall, ticketDate, status);
    }

    @BeforeEach
    void setUp(){
        ticketRepository = new MemoryTicketRepository();
        ticketMonitor = new TicketMonitor();
        ticketService = new TicketService(ticketRepository);

        user = new User(1);
        seat = setSeat(SeatStatus.AVAILABLE);
    }

    @Test
    @DisplayName("좌석이 이미 예약되었을 경우, 예약할 수 없다.")
    void cannot_reserve_if_seat_is_already_taken(){
        seat.updateStatus(SeatStatus.RESERVED);

        assertThrows(UnAvailableSeatException.class, () -> ticketService.register(user, seat));
    }

    @Test
    @DisplayName("티켓을 유저 ID로 조회한다.")
    void query_ticket_by_user_id(){
        ticketService.register(user, seat);

        Ticket ticket = ticketService.query(user.getUserId());

        assertThat(ticket.getUserId()).isEqualTo(user.getUserId());
        assertThat(ticket.getSeatNo()).isEqualTo(seat.getSeatNo());
    }

    @Test
    @DisplayName("티켓은 좌석정보와 날짜를 포함한다.")
    void ticket_includes_seat_info_and_date(){
        Ticket ticket = ticketService.register(user, seat);

        assertThat(ticket.getDateInfo()).isEqualTo(ticketDate);
        assertThat(ticket.getSeatNo()).isEqualTo(seatNo);
        assertThat(ticket.getConcertHall()).isEqualTo(concertHall);
    }


    @Test
    @DisplayName("결제 직후, 티켓 상태는 예약대기.")
    void ticket_status_is_pending_before_payment(){
        //given 티켓이 발행되었는데
        Ticket ticket = ticketService.register(user, seat);

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.PENDING);
    }

    @Test
    @DisplayName("결제시간이 5분 지날 때까지 완료하지 않으면 예약취소")
    void ticket_cancelled_if_not_paid_in_5_mins(){

        Ticket ticket = ticketService.register(user, seat);

        //when: 5분이 지났을 경우
        ticketMonitor.checkPendingTicket(ticket, ticket.getReservedTime().plusMinutes(6));
        //then: ticket 상태는 취소
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.CANCELLED);
    }



}
