package hhplus.ticketing.domain.token.repository;

import hhplus.ticketing.domain.token.models.Token;

import java.time.LocalDateTime;

public interface TokenManager {
    public Token issue(long concertId, long userId, LocalDateTime issuedAt);

    boolean validate(Token token);
}
