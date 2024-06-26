package hhplus.ticketing.domain.watingqueue.models;

import java.time.LocalDateTime;

public class Token {

    String claim;
    long userId;
    TokenStatus status;
    LocalDateTime issuedAt;

    public Token(String claim, long userId, LocalDateTime issuedAt) {
        this.claim = claim;
        this.userId = userId;
        this.status = TokenStatus.WAITING;
        this.issuedAt = issuedAt;
    }

    public String getClaim() {
        return claim;
    }

    public long getUserId() {
        return userId;
    }

    public TokenStatus getStatus() {
        return status;
    }

    public void updateStatus(TokenStatus status){
        this.status = status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}
