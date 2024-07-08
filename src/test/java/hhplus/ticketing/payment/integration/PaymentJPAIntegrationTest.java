package hhplus.ticketing.payment.integration;

import hhplus.ticketing.api.ticket.facade.TicketFacade;
import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.components.ConcertWriter;
import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.payment.components.PaymentService;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.infra.TicketJPARepository;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.api.payment.facade.PaymentFacade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PaymentJPAIntegrationTest {

    @Autowired
    ConcertWriter concertWriter;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @Autowired
    TicketJPARepository ticketRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketFacade ticketFacade;

    @Autowired
    PaymentFacade paymentFacade;

    private Seat setSeat() {
        Seat seat = Seat.builder()
                        .seatNo(2)
                        .concertName("아이유 10주년 콘서트")
                        .concertHall(ConcertHall.LOTTE_TOWER)
                        .showTime(LocalDateTime.of(2024,7,13,15,0,0))
                        .status(SeatStatus.AVAILABLE)
                        .build();
        concertWriter.registerSeat(seat);
        return seat;
    }

    @BeforeEach
    void setUp(){
        ticketRepository.deleteAll();
    }

    private User setUser(long balance){
        User user = new User(6, balance);
        userService.save(user);
        return user;
    }

    @Test
    @DisplayName("사용자가 보유한 잔액이 부족하면 결제를 실패한다.")
    void fail_payment_when_point_is_not_enough(){
        User user = setUser(0);
        Seat seat = setSeat();
        Ticket ticket = ticketFacade.register(user.getId(), 100000, seat.getConcertId(), seat.getShowTime(), seat.getSeatNo());


        assertThrows(InsufficientBalanceException.class, () ->
                paymentFacade.processPayment(ticket.getId(), ticket.getPrice(), user.getId(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("결제완료시 사용자 잔액이 차감된다.")
    void deduct_point_when_payment_complete(){
        User user = setUser(200000);
        Seat seat = setSeat();

        Ticket ticket = ticketFacade.register(user.getId(), 100000, seat.getConcertId(), seat.getShowTime(), seat.getSeatNo());

        paymentFacade.processPayment(ticket.getId(), ticket.getPrice(), user.getId(), LocalDateTime.now());
        User foundUser = userService.findById(user.getId());
        assertThat(foundUser.getBalance()).isEqualTo(100000);
    }

    @Test
    @DisplayName("결제완료시 티켓 상태가 '예약'으로 변경된다.")
    void ticket_status_reserved_when_payment_complete(){
        User user = setUser(200000);
        Seat seat = setSeat();

        Ticket ticket = ticketFacade.register(user.getId(), 100000, seat.getConcertId(), seat.getShowTime(), seat.getSeatNo());

        paymentFacade.processPayment(ticket.getId(), ticket.getPrice(), user.getId(), LocalDateTime.now());
        List<Ticket> foundTicketList = ticketService.findByUserId(user.getId());

        assertThat(foundTicketList.get(0).getStatus()).isEqualTo(TicketStatus.REGISTERED);
    }

    @Test
    @DisplayName("결제완료시 결제내역이 남는다.")
    void payment_left_payment_transaction(){
        User user = setUser(200000);
        Seat seat = setSeat();

        Ticket ticket = ticketFacade.register(user.getId(), 100000, seat.getConcertId(), seat.getShowTime(), seat.getSeatNo());

        paymentFacade.processPayment(ticket.getId(), ticket.getPrice(), user.getId(), LocalDateTime.now());
        List<PaymentTransaction> transactions = paymentService.findTransactionHistory(user.getId());

        PaymentTransaction transaction = transactions.get(0);

        assertThat(transaction.userId()).isEqualTo(user.getId());
        assertThat(transaction.price()).isEqualTo(ticket.getPrice());
    }

}
