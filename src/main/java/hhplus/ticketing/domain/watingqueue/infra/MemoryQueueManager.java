package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.TokenStatus;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MemoryQueueManager implements QueueManager {
    long ACTIVE_TIME= 5 * 1000 * 60;

    Map<Long, WaitingInfo> tokenMap = new HashMap<>();

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        return tokenMap.get(token.getUserId());
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        WaitingInfo waitingInfo = new WaitingInfo(token.getUserId(), tokenMap.size()+1);
        tokenMap.put(token.getUserId(), waitingInfo);
        return waitingInfo;
    }

    @Override
    public void deleteFromWaitingQueue(Token token) {

    }

    public void activate(Token token) {
        token.updateStatus(TokenStatus.ACTIVE);
    }

    public void deactivate(Token token) {
        token.updateStatus(TokenStatus.EXPIRED);
    }

    public boolean checkExpired(Token token) {
        return token.getIssuedAt().plusSeconds(ACTIVE_TIME).isAfter(LocalDateTime.now());
    }
}
