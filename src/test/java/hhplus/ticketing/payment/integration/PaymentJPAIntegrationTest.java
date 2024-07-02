package hhplus.ticketing.payment.integration;

import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.payment.components.PaymentService;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.api.payment.facade.PaymentFacade;
import org.assertj.core.api.Assertions;
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
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @Autowired
    PointService pointService;

    @Autowired
    TicketService ticketService;

    @Autowired
    PaymentFacade paymentFacade;

    private Seat setSeat(long price) {
        return  Seat.builder()
                .seatNo(1)
                .concertName("아이유 10주년 콘서트")
                .concertHall(ConcertHall.JAMSIL)
                .showTime(LocalDateTime.now())
                .price(100000)
                .status(SeatStatus.RESERVED)
                .build();

    }

    private User setUser(long balance){
        User user = new User(1, balance);

        return user;
    }

    @Test
    @DisplayName("사용자가 보유한 잔액이 부족하면 결제를 실패한다.")
    void fail_payment_when_point_is_not_enough(){
        User user = setUser(0);
        Seat seat = setSeat(100000);
        Ticket ticket = new Ticket(seat, user);



        assertThrows(InsufficientBalanceException.class, () ->
                paymentFacade.processPayment(ticket, user, LocalDateTime.now()));
    }

    @Test
    @DisplayName("결제완료시 사용자 잔액이 차감된다.")
    void deduct_point_when_payment_complete(){
        User user = setUser(200000);
        Seat seat = setSeat(100000);
        Ticket ticket = new Ticket(seat, user);

        paymentFacade.processPayment(ticket, user, LocalDateTime.now());
        User foundUser = userService.findById(user.getUserId());
        assertThat(foundUser.getBalance()).isEqualTo(100000);
    }

    @Test
    @DisplayName("결제완료시 티켓 상태가 '예약'으로 변경된다.")
    void ticket_status_reserved_when_payment_complete(){
        User user = setUser(200000);
        Seat seat = setSeat(100000);
        Ticket ticket = new Ticket(seat, user);


        paymentFacade.processPayment(ticket, user, LocalDateTime.now());
        Ticket foundTicket = ticketService.query(user.getUserId());

        assertThat(foundTicket.getStatus()).isEqualTo(TicketStatus.REGISTERED);
    }

    @Test
    @DisplayName("결제완료시 결제내역이 남는다.")
    void payment_left_payment_transaction(){
        User user = setUser(200000);
        Seat seat = setSeat(100000);
        Ticket ticket = new Ticket(seat, user);

        paymentFacade.processPayment(ticket, user, LocalDateTime.now());
        List<PaymentTransaction> transactions = paymentService.findTransactionHistory(user.getUserId());

        PaymentTransaction transaction = transactions.get(0);

        Assertions.assertThat(transaction.userId()).isEqualTo(user.getUserId());
        Assertions.assertThat(transaction.price()).isEqualTo(ticket.getPrice());
        Assertions.assertThat(transaction.ticketId()).isEqualTo(ticket.getId());
    }

}
