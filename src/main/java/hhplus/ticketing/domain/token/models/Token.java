package hhplus.ticketing.domain.token.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Token {

    long concertId;
    String jwt;
    LocalDateTime issuedAt;

    public Token(long concertId, String jwt) {
        this.concertId = concertId;
        this.jwt = jwt;
    }

    public long convertToTimeScale(){
        return issuedAt.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
