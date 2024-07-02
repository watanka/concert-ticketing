package hhplus.ticketing.domain.payment.infra;

import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.payment.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentTransactionRepositoryImpl implements PaymentTransactionRepository {

    @Autowired
    PaymentTransactionJPARepository paymentTransactionJPARepository;

    @Override
    public PaymentTransaction save(PaymentTransaction paymentTransaction) {
        paymentTransactionJPARepository.save(PaymentTransactionEntity.from(paymentTransaction));
        return paymentTransaction;
    }

    @Override
    public List<PaymentTransaction> findByUserId(long userId) {

        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionJPARepository.findAllByUserId(userId);

        return paymentTransactionEntityList.stream()
                .map(PaymentTransactionEntity::to)
                .toList();
    }
}
