package hhplus.ticketing.domain.watingqueue.components;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.TokenStatus;

import java.time.LocalDateTime;

public class TokenManager {

    long ACTIVE_TIME= 5 * 1000 * 60;

    public void activate(Token token) {
        token.updateStatus(TokenStatus.ACTIVE);
    }

    public void deactivate(Token token) {
        token.updateStatus(TokenStatus.EXPIRED);
    }

    public boolean checkExpired(Token token) {
        return token.getIssuedAt().plusSeconds(ACTIVE_TIME).isAfter(LocalDateTime.now());
    }
}
