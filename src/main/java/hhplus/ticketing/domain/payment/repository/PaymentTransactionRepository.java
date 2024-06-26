package hhplus.ticketing.domain.payment.repository;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;

import java.util.List;

public interface PaymentTransactionRepository {
    PaymentTransaction save(PaymentTransaction paymentTransaction);
    List<PaymentTransaction> findByUserId(long userId);
}
