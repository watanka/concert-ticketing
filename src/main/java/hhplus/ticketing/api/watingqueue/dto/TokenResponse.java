package hhplus.ticketing.api.watingqueue.dto;

import hhplus.ticketing.domain.token.models.Token;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TokenResponse(
        long concertId, String jwt, LocalDateTime issuedAt
) {

    public static TokenResponse from(Token token){
        return TokenResponse.builder()
                .concertId(token.getConcertId())
                .jwt(token.getJwt())
                .issuedAt(token.getIssuedAt())
                .build();
    }
}
