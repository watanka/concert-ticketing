package hhplus.ticketing.domain.user.infra;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import hhplus.ticketing.domain.user.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name="`user`")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {
    @Id
    long id;
    @Column(name="balance")
    private long balance;


    public static UserEntity from(User user) {

        return UserEntity.builder()
                .id(user.getId())
                .balance(user.getBalance())
                .build();
    }

    public static User to(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .balance(userEntity.getBalance())
                .build();
    }

}
