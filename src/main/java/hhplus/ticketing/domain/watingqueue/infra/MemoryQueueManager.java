package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryQueueManager implements QueueManager {
    private final long ACTIVE_TIME= 5 * 60;

    Map<String, WaitingInfo> tokenMap = new HashMap<>();
    Map<String, LocalDateTime> activatedTokenMap = new HashMap<>();

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        return tokenMap.get(token.getClaim());
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        WaitingInfo waitingInfo = new WaitingInfo(token.getUserId(), tokenMap.size()+1, token.getIssuedAt());
        tokenMap.put(token.getClaim(), waitingInfo);
        return waitingInfo;
    }

    @Override
    public void deleteFromWaitingQueue(Token token) {
        tokenMap.remove(token.getClaim());
    }

    @Override
    public void activate(Token token) {

    }

    public void activate(Token token, LocalDateTime activatedTime) {
        deleteFromWaitingQueue(token);
        activatedTokenMap.put(token.getClaim(), activatedTime); // map의 value로 넣을 값은 변경여지가 있다.
    }

    public void deactivate(Token token) {
        activatedTokenMap.remove(token.getClaim());
    }

    @Override
    public boolean checkExpired(Token token, LocalDateTime now) {
        LocalDateTime activatedTime = activatedTokenMap.get(token.getClaim());
        return activatedTime.plusSeconds(ACTIVE_TIME).isBefore(now);
    }

    @Override
    public void deleteAll() {
        tokenMap.clear();
    }

    @Override
    public void activateTokensByTimeOrder(long concertId, int n) {
        List<Map.Entry<String, WaitingInfo>> sortedNWaitingInfo = tokenMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(n)
                .toList();

        for (Map.Entry<String, WaitingInfo> entry : sortedNWaitingInfo) {
            tokenMap.remove(entry.getKey());
            activatedTokenMap.put(entry.getKey(), entry.getValue().issuedAt());
        }

    }

    @Override
    public List<String> getTokensInWaitingQueue(long concertId) {
        return List.of();
    }

    @Override
    public List<String> getActivatedTokens(long concertId) {
        return List.of();
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

        System.out.println(activatedTokenMap);
    }

    @Override
    public boolean isExpired(Token token) {
        return !activatedTokenMap.containsKey(token.getClaim());
    }
}
