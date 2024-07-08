package hhplus.ticketing.domain.user.components;

import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User findById(long userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public User updateBalance(long userId, Point point){
        User user = userRepository.findById(userId);
        user.updatePoint(point);
        userRepository.save(user);
        return user;
    }

}
