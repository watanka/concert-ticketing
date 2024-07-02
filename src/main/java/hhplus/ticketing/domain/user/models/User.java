package hhplus.ticketing.domain.user.models;

import hhplus.ticketing.base.exceptions.NotEnoughBalanceException;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    long userId;
    long balance;

    public User(long userId, long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public void updatePoint(Point point) {
        if (point.getType() == PointType.RECHARGE) rechargePoint(point);

        else if (point.getType() == PointType.USE) {
            if (point.getAmount() > balance) throw new NotEnoughBalanceException();
            usePoint(point);
        }
    }

    private void rechargePoint(Point point){
        this.balance += point.getAmount();
    }

    private void usePoint(Point point){
        this.balance -= point.getAmount();
    }


    public long getUserId() {
        return userId;
    }

    public long getBalance() {
        return balance;
    }



}
