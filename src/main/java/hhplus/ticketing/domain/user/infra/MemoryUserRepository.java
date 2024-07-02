package hhplus.ticketing.domain.user.infra;

import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.user.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {

    Map<Long, User> userMap = new HashMap<>();

    @Override
    public User save(User user) {
        return userMap.put(user.getUserId(), user);
    }

    @Override
    public User findById(long userId) {
        return userMap.get(userId);
    }
}
