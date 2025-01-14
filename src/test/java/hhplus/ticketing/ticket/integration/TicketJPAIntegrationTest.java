package hhplus.ticketing.ticket.integration;

import hhplus.ticketing.base.exceptions.UnavailableSeatException;
import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.ticket.components.TicketMonitor;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.infra.TicketEntity;
import hhplus.ticketing.domain.ticket.infra.TicketJPARepository;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TicketJPAIntegrationTest {

    @Autowired
    TicketJPARepository ticketRepository;

    @Autowired
    TicketService ticketService;
    @Autowired
    TicketMonitor ticketMonitor;

    LocalDateTime ticketDate = LocalDateTime.of(2024, 3, 5, 17, 5);

    String concertName = "아이유 10주년 콘서트";
    ConcertHall concertHall = ConcertHall.LOTTE_TOWER;
    long seatNo = 1;
    User user;
    Seat seat;


    private Seat setSeat(SeatStatus status){
        return  Seat.builder()
                .seatNo(seatNo)
                .concertId(1)
                .concertName(concertName)
                .concertHall(concertHall)
                .showTime(ticketDate)
                .status(status)
                .build();
    }

    @BeforeEach
    void setUp(){
        ticketRepository.deleteAll();
        user = new User(1, 0);
        seat = setSeat(SeatStatus.AVAILABLE);
    }

    @Test
    @DisplayName("좌석이 이미 예약되었을 경우, 예약할 수 없다.")
    void cannot_reserve_if_seat_is_already_taken(){
        ticketService.register(user.getId(),100000, seat, LocalDateTime.now());

        assertThrows(UnavailableSeatException.class, () ->
                ticketService.register(user.getId(),100000, seat, LocalDateTime.now()));
    }

    @Test
    @DisplayName("티켓을 유저 ID로 조회한다.")
    void query_ticket_by_user_id(){
        ticketService.register(user.getId(),100000, seat, LocalDateTime.now());

        List<Ticket> ticket = ticketService.findByUserId(user.getId());

        assertThat(ticket.get(0).getUserId()).isEqualTo(user.getId());
        assertThat(ticket.get(0).getSeatNo()).isEqualTo(seat.getSeatNo());
    }

    @Test
    @DisplayName("티켓은 좌석정보와 날짜를 포함한다.")
    void ticket_includes_seat_info_and_date(){
        Ticket ticket = ticketService.register(user.getId(),100000, seat, LocalDateTime.now());

        List<Ticket> foundTicketList = ticketService.findByUserId(user.getId());


        assertThat(foundTicketList.get(0).getShowTime()).isEqualTo(ticketDate);
        assertThat(foundTicketList.get(0).getSeatNo()).isEqualTo(seatNo);
        assertThat(foundTicketList.get(0).getConcertHall()).isEqualTo(concertHall);
    }


    @Test
    @DisplayName("결제 직후, 티켓 상태는 예약대기.")
    void ticket_status_is_pending_before_payment(){
        //given 티켓이 발행되었는데
        ticketService.register(user.getId(),100000, seat, LocalDateTime.now());

        List<Ticket> foundTicketList = ticketService.findByUserId(user.getId());

        assertThat(foundTicketList.get(0).getStatus()).isEqualTo(TicketStatus.PENDING);
    }

    @Test
    @Disabled
    @DisplayName("예약시점에서 5분이상 지나면 예약취소")
    void ticket_cancelled_if_not_paid_in_5_mins(){

        Ticket ticket = ticketService.register(user.getId(),100000, seat, LocalDateTime.now());

        //when: 5분이 지났을 경우
        ticketMonitor.cancelPendingTickets(ticket, ticket.getReservedTime().plusMinutes(6));

        Ticket foundTicket = ticketService.findById(ticket.getId());
        //then: ticket 상태는 취소
        assertThat(foundTicket.getStatus()).isEqualTo(TicketStatus.CANCELLED);
    }
}
