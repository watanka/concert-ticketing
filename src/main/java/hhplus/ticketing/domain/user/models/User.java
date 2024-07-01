package hhplus.ticketing.domain.user.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private long userId;
    private long balance;

    public User(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public void rechargePoint(long pointAmount) {
        balance += pointAmount;
    }

    public void usePoint(long pointAmount) {
        if (pointAmount > balance){
            throw new IllegalArgumentException();
        }
        balance -= pointAmount;
    }


    public long getUserId() {
        return userId;
    }

    public long getBalance() {
        return balance;
    }



}
