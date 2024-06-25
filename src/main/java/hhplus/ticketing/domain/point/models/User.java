package hhplus.ticketing.domain.point.models;

import lombok.Getter;

@Getter
public class User {
    private long userId;
    private long balance;

    public User(long userId) {
        this.userId = userId;
        this.balance = 0;
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
