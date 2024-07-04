package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MemoryActiveQueueManager implements ActiveQueueManager{

    private final long ACTIVE_TIME= 5 * 60;
    Map<String, LocalDateTime> activatedTokenMap = new HashMap<>();
    @Override
    public void activate(Token token, LocalDateTime activatedTime) {
        activatedTokenMap.put(token.getClaim(), activatedTime);
    }

    @Override
    public void deactivate(Token token) {
        activatedTokenMap.remove(token.getClaim());
    }

    @Override
    public boolean checkExpired(Token token, LocalDateTime now) {
        if(!checkActive(token)) return true;
        LocalDateTime activatedTime = activatedTokenMap.get(token.getClaim());
        return activatedTime.plusSeconds(ACTIVE_TIME).isBefore(now);
    }

    @Override
    public void activate(Map.Entry<String, WaitingInfo> entry, LocalDateTime now) {
        activatedTokenMap.put(entry.getKey(), entry.getValue().issuedAt());
    }

    @Override
    public boolean checkActive(Token token) {
        return activatedTokenMap.containsKey(token.getClaim());
    }

    @Override
    public void expireTokens(LocalDateTime now) {
        activatedTokenMap.entrySet()
                .removeIf(issuedAt ->
                        issuedAt.getValue().plusSeconds(ACTIVE_TIME) // 토큰 활성 만료시간
                                .isBefore(now));
    }

    @Override
    public boolean isExpired(Token token) {
        return !activatedTokenMap.containsKey(token.getClaim());
    }

}
