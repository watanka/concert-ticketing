package hhplus.ticketing.domain.user.infra;

import hhplus.ticketing.domain.user.infra.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
    UserEntity save(UserEntity userEntity);
    UserEntity findById(long userId);

}
