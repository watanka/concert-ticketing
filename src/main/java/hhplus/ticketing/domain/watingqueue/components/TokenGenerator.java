package hhplus.ticketing.domain.watingqueue.components;

import hhplus.ticketing.domain.watingqueue.models.Token;

import java.time.LocalDateTime;

public interface TokenGenerator {
    public Token issue(long concertId, long userId, LocalDateTime issuedAt);
}
