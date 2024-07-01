package hhplus.ticketing.domain.payment.components;

import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.payment.infra.MemoryPaymentTransactionRepository;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.payment.repository.PaymentTransactionRepository;
import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.infra.MemoryPointRepository;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.point.repository.PointRepository;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.infra.MemoryTicketRepository;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentService {
    PointRepository pointRepository = new MemoryPointRepository();
    PointService pointService = new PointService(pointRepository);
    TicketRepository ticketRepository = new MemoryTicketRepository();
    TicketService ticketService = new TicketService(ticketRepository);

    PaymentTransactionRepository paymentTransactionRepository = new MemoryPaymentTransactionRepository();

    public PaymentTransaction processPayment(Ticket ticket, User user) {
        if (user.getBalance() < ticket.getPrice()){
            throw new InsufficientBalanceException();
        }
        pointService.usePoint(user, ticket.getPrice());
        ticketService.confirmPayment(ticket);

        PaymentTransaction paymentTransaction = new PaymentTransaction(user.getUserId(), ticket.getPrice(), ticket.getId(), LocalDateTime.now());
        paymentTransactionRepository.save(paymentTransaction);

        return paymentTransaction;
    }

    public List<PaymentTransaction> findTransactionHistory(long userId) {
        return paymentTransactionRepository.findByUserId(userId);
    }
}
