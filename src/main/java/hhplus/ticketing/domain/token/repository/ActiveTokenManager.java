package hhplus.ticketing.domain.token.repository;

import hhplus.ticketing.domain.token.models.Token;

import java.time.LocalDateTime;

public interface ActiveTokenManager {

    void activate(Token token);

    void deactivate(Token token);

    boolean checkActive(Token token);

    void expireTokens(long concertId, LocalDateTime now);

    boolean checkExpired(Token token);

    void clear();
}
