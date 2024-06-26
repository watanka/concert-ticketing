package hhplus.ticketing.domain.payment.infra;

import com.github.ansell.jdefaultdict.JDefaultDict;
import hhplus.ticketing.domain.payment.models.PaymentTransaction;
import hhplus.ticketing.domain.payment.repository.PaymentTransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemoryPaymentTransactionRepository implements PaymentTransactionRepository {

    Map<Long, List<PaymentTransaction>> transactionHistory = new JDefaultDict<>(k -> new ArrayList<>());

    public PaymentTransaction save(PaymentTransaction paymentTransaction){
        List<PaymentTransaction> transactionHistoryByUser = transactionHistory.get(paymentTransaction.userId());
        transactionHistoryByUser.add(paymentTransaction);

        return paymentTransaction;
    }

    @Override
    public List<PaymentTransaction> findByUserId(long userId) {
        return transactionHistory.get(userId);
    }
}
