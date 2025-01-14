package hhplus.ticketing.api.payment.facade;

import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.payment.components.PaymentService;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
    @Autowired
    private final UserService userService;
    @Autowired
    private final TicketService ticketService;
    @Autowired
    private final PointService pointService;
    @Autowired
    private final PaymentService paymentService;

    public PaymentTransaction processPayment(long ticketId, long price, long userId, LocalDateTime now) {
            Point payPoint = new Point(price, PointType.USE);

            userService.updateBalance(userId, payPoint);

            pointService.recordPointTransaction(userId, payPoint, now);
            ticketService.confirmPayment(ticketId);

            PaymentTransaction paymentTransaction = paymentService.recordPaymentTransaction(ticketId, price, userId);

            return paymentTransaction;
        }
    }