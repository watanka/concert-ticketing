package hhplus.ticketing.domain.user.models;

import hhplus.ticketing.base.exceptions.InsufficientBalanceException;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class User {
    long id;
    long balance;

    public void updatePoint(Point point) {
        if (point.getType() == PointType.RECHARGE) rechargePoint(point);

        else if (point.getType() == PointType.USE) {
            if (point.getAmount() > balance) throw new InsufficientBalanceException();
            usePoint(point);
        }
    }

    private void rechargePoint(Point point){
        this.balance += point.getAmount();
    }

    private void usePoint(Point point){
        this.balance -= point.getAmount();
    }


    public long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }



}
