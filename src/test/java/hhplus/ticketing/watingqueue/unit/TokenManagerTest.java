package hhplus.ticketing.watingqueue.unit;

import hhplus.ticketing.domain.token.repository.TokenManager;
import hhplus.ticketing.domain.token.infra.token.JwtTokenManager;
import hhplus.ticketing.domain.token.models.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenManagerTest {


    TokenManager tokenManager = new JwtTokenManager();

    @Test
    @DisplayName("TokenGenerator가 만든 토큰에서 필요한 정보를 추출할 수 있다. {concertId, userId, issuedAt}")
    void tokengenerator_issue_token_by_its_rule(){


        long concertId = 1;
        long userId = 1;
        LocalDateTime issuedAt = LocalDateTime.now();

        Token token = tokenManager.issue(concertId, userId, issuedAt);

        assertThat(token.getConcertId()).isEqualTo(concertId);
        assertThat(token.getIssuedAt()).isEqualTo(issuedAt);

    }

    @Test
    @DisplayName("토큰의 유효성을 검증한다")
    void token_can_be_validated(){
        long concertId = 1;
        long userId = 1;
        String invalidClaim = "FAKE_CLAIM";
        LocalDateTime issuedAt = LocalDateTime.now();

        Token validToken = tokenManager.issue(concertId, userId, issuedAt);
        Token invalidToken = new Token(concertId, invalidClaim, issuedAt);


        assertThat(tokenManager.validate(validToken)).isTrue();
        assertThat(tokenManager.validate(invalidToken)).isFalse();
    }

    @Test
    @DisplayName("유효기간이 지나면 토큰은 만료된다.")
    void token_expired_when_time_passed(){
        long validationTime = 30 * 60 + 10 * 60; // buffer
        long concertId = 1;
        long userId = 1;
        LocalDateTime issuedTimePassed = LocalDateTime.now().minusMinutes(validationTime);

        Token expiredToken = tokenManager.issue(concertId, userId, issuedTimePassed);

        assertThat(tokenManager.validate(expiredToken)).isFalse();
    }


}
