package hhplus.ticketing.domain.token.components;

import hhplus.ticketing.domain.token.models.Token;

import java.time.LocalDateTime;

public interface TokenGenerator {
    public Token issue(long concertId, long userId, LocalDateTime issuedAt);
}
