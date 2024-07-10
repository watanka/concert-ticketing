package hhplus.ticketing.domain.token.infra.token;

import hhplus.ticketing.domain.token.repository.TokenManager;
import hhplus.ticketing.domain.token.models.Token;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Repository
public class JwtTokenManager implements TokenManager {
    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long EXPIRATION_PERIOD = 30 * 60;


    public Token issue(long concertId, long userId, LocalDateTime issuedAt){
        String jws = Jwts.builder()
                .issuedAt(toDate(issuedAt))
                .expiration(toDate(issuedAt.plusSeconds(EXPIRATION_PERIOD)))
                .id("CONCERT:"+concertId+"-USER:"+userId)
                .signWith(key)
                .compact();

        return new Token(concertId, jws, issuedAt);
    }



    @Override
    public boolean validate(Token token) {
        try{
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parse(token.getJwt());
            return true;
        } catch (JwtException e){

        }
        return false;
    }

    public Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
