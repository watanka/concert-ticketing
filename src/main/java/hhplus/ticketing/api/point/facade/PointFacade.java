package hhplus.ticketing.api.point.facade;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.user.components.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PointFacade {
    @Autowired
    private final PointService pointService;

    @Autowired
    private final UserService userService;

    @DistributedLock(key="#userId")
    public void transact(long userId, Point point) {
        pointService.recordPointTransaction(userId, point, LocalDateTime.now());
        userService.updateBalance(userId, point);
    }
}
