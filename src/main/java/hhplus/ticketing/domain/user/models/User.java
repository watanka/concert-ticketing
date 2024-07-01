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
        if (point.getType() == PointType.RECHARGE){
            balance += point.getAmount();
        }else{
            if (point.getAmount() > balance){
                throw new NotEnoughBalanceException();
            }
            balance -= point.getAmount();
        }

    }


    public long getUserId() {
        return userId;
    }

    public long getBalance() {
        return balance;
    }



}
