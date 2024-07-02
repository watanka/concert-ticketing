package hhplus.ticketing.point.integration;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PointUseCase {
    @Autowired
    PointService pointService;

    @Autowired
    UserService userService;

    public void transact(User user, Point point) {
        pointService.recordPointTransaction(user.getUserId(), point, LocalDateTime.now());
        userService.updateBalance(user, point);
    }
}
