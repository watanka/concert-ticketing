package hhplus.ticketing.domain.token.models;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Getter
public class Token {

    long concertId;
    String claim;
    long userId;
    TokenStatus status;
    LocalDateTime issuedAt;

    public Token(long concertId, String claim, LocalDateTime issuedAt){
        this.concertId = concertId;
        this.claim = claim;
        this.issuedAt = issuedAt;
    }

    public void updateStatus(TokenStatus status){
        this.status = status;
    }
    public long getTimeScale(){
        return issuedAt.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
