package hhplus.ticketing.domain.payment.components;

import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.payment.infra.MemoryPaymentTransactionRepository;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.payment.repository.PaymentTransactionRepository;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.infra.MemoryPointRepository;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.infra.MemoryUserRepository;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.point.repository.PointRepository;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.infra.MemoryTicketRepository;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PointService pointService;


    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;


    public PaymentTransaction processPayment(Ticket ticket, User user, LocalDateTime time) {
        Point payPoint = new Point(ticket.getPrice(), PointType.USE);

        userService.updateBalance(user, payPoint);

        pointService.recordPointTransaction(user.getUserId(), payPoint, time);
        ticketService.confirmPayment(ticket);

        PaymentTransaction paymentTransaction = recordPaymentTransaction(ticket, user);

        return paymentTransaction;
    }

    public List<PaymentTransaction> findTransactionHistory(long userId) {
        return paymentTransactionRepository.findByUserId(userId);
    }

    public PaymentTransaction recordPaymentTransaction(Ticket ticket, User user) {
        PaymentTransaction paymentTransaction = new PaymentTransaction(user.getUserId(), ticket.getPrice(), ticket.getId(), LocalDateTime.now());
        paymentTransactionRepository.save(paymentTransaction);
        return paymentTransaction;
    }
}
