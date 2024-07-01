package hhplus.ticketing.domain.user.infra;

import hhplus.ticketing.domain.user.models.User;
import hhplus.ticketing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJPARepository userJPARepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = userJPARepository.save(UserEntity.from(user));
        return UserEntity.to(userEntity);
    }

    @Override
    public User findById(long userId) {
        UserEntity userEntity = userJPARepository.findById(userId);
        return UserEntity.to(userEntity);
    }
}
