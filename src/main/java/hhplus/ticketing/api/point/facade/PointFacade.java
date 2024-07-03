package hhplus.ticketing.api.point.facade;

import hhplus.ticketing.domain.point.components.PointService;
import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.user.components.UserService;
import hhplus.ticketing.domain.user.models.User;
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

    @DistributedLock(key="#user.getUserId()")
    public void transact(User user, Point point) {
        pointService.recordPointTransaction(user.getId(), point, LocalDateTime.now());
        userService.updateBalance(user, point);
    }
}
