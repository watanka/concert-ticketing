package hhplus.ticketing.domain.user.repository;

import hhplus.ticketing.domain.user.models.User;

public interface UserRepository {
    public User save(User user);
    public User findById(long userId);
}
