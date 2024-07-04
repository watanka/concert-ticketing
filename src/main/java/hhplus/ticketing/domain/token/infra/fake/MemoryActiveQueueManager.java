package hhplus.ticketing.domain.token.infra.fake;

import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.repository.ActiveTokenManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MemoryActiveQueueManager implements ActiveTokenManager {

    private final long ACTIVE_TIME= 5 * 60;
    Map<String, LocalDateTime> activatedTokenMap = new HashMap<>();


    @Override
    public void activate(Token token) {
        activatedTokenMap.put(token.getClaim(), token.getIssuedAt());
    }

    @Override
    public void deactivate(Token token) {
        activatedTokenMap.remove(token.getClaim());
    }


    @Override
    public boolean checkActive(Token token) {
        return activatedTokenMap.containsKey(token.getClaim());
    }

    @Override
    public void expireTokens(long concertId, LocalDateTime now) {
        activatedTokenMap.entrySet()
                .removeIf(issuedAt ->
                        issuedAt.getValue().plusSeconds(ACTIVE_TIME) // 토큰 활성 만료시간
                                .isBefore(now));
    }

    @Override
    public boolean checkExpired(Token token) {
        return !activatedTokenMap.containsKey(token.getClaim());
    }

    @Override
    public void clear() {
        activatedTokenMap.clear();
    }

}
