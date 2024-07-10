package hhplus.ticketing.api.watingqueue.dto;

import hhplus.ticketing.domain.token.models.Token;
import lombok.Builder;

@Builder
public record TokenQueryRequest(long concertId, String jwt) {
    public Token toDomain(){
        return Token.builder()
                .concertId(concertId)
                .jwt(jwt)
                .build();
    }

    public static TokenQueryRequest from(Token token){
        return TokenQueryRequest.builder()
                .concertId(token.getConcertId())
                .jwt(token.getJwt())
                .build();
    }

}
