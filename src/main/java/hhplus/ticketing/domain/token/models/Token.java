package hhplus.ticketing.domain.token.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Builder
@Getter
public class Token {

    long concertId;
    String jwt;
    long userId;
    LocalDateTime issuedAt;

    public Token(long concertId, String jwt, LocalDateTime issuedAt) {
        this.concertId = concertId;
        this.jwt = jwt;
        this.issuedAt = issuedAt;
    }

    public long getTimeScale(){
        return issuedAt.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
