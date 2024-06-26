package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.components.TokenGenerator;
import hhplus.ticketing.domain.watingqueue.models.Token;

import java.time.LocalDateTime;

public class MemoryTokenGenerator implements TokenGenerator {
    public Token issue(long userId) {
        return new Token(String.valueOf(userId), userId, LocalDateTime.now());
    }
}
