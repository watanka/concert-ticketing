package hhplus.ticketing.domain.payment.infra;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionJPARepository extends JpaRepository<PaymentTransactionEntity, Long> {

    PaymentTransactionEntity save(PaymentTransactionEntity paymentTransactionEntity);
    List<PaymentTransactionEntity> findAllByUserId(long userId);
}
