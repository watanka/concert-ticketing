package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.components.TokenGenerator;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.TokenStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class MemoryTokenGenerator implements TokenGenerator {
    public Token issue(long concertId, long userId, LocalDateTime issuedAt) {
        return new Token(concertId, String.valueOf(userId), userId, TokenStatus.WAITING, issuedAt);
    }
}
