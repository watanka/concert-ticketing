package hhplus.ticketing.domain.payment.components;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.payment.repository.PaymentTransactionRepository;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.ticket.models.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Autowired
    private final PaymentTransactionRepository paymentTransactionRepository;

    public List<PaymentTransaction> findTransactionHistory(long userId) {
        return paymentTransactionRepository.findByUserId(userId);
    }

    public PaymentTransaction recordPaymentTransaction(long ticketId, long price, long userId) {
        PaymentTransaction paymentTransaction = new PaymentTransaction(userId, price, ticketId, LocalDateTime.now());
        paymentTransactionRepository.save(paymentTransaction);
        return paymentTransaction;
    }
}
