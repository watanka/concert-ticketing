package hhplus.ticketing.domain.watingqueue.components;

import hhplus.ticketing.domain.watingqueue.models.Token;

public interface TokenGenerator {
    public Token issue(long concertId, long userId);
}
