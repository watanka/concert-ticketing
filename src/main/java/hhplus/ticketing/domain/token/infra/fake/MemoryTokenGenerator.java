package hhplus.ticketing.domain.token.infra.fake;

import hhplus.ticketing.domain.token.components.TokenGenerator;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.TokenStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class MemoryTokenGenerator implements TokenGenerator {
    public Token issue(long concertId, long userId, LocalDateTime issuedAt) {
        return new Token(concertId, String.valueOf(userId), userId, issuedAt);
    }
}
