package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MemoryQueueManager implements QueueManager {


    private final WaitingQueueManager waitingQueueManager;
    private final ActiveQueueManager activeQueueManager;

    @Override
    public boolean exists(String keyName, Token token) {
        return activeQueueManager.isExpired(token);
    }

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        return waitingQueueManager.getWaitingInfoByToken(token);
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        return waitingQueueManager.insertInWaitingQueue(token);
    }


    public void activate(Token token, LocalDateTime activatedTime) {
        waitingQueueManager.deleteFromWaitingQueue(token);
        activeQueueManager.activate(token, activatedTime); // map의 value로 넣을 값은 변경여지가 있다.
    }

    public void deactivate(Token token) {
        activeQueueManager.deactivate(token);

    }

    @Override
    public boolean checkExpired(Token token, LocalDateTime now) {
        return activeQueueManager.checkExpired(token, now);
    }

    @Override
    public void deleteAll() {
        waitingQueueManager.clear();
    }

    @Override
    public void activateTokensByTimeOrder(long concertId, int n) {

        List<String> sortedNWaitingInfo = waitingQueueManager.getNTokensByTimeOrder(concertId, n);

        for (String claim : sortedNWaitingInfo) {
            LocalDateTime now = LocalDateTime.now();
            Token newToken = new Token(concertId, claim, now); // 임시 우회. activeQueueManager 인터페이스 맞추기 위해서...

            waitingQueueManager.remove(newToken);
            activeQueueManager.activate(newToken, now);
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
        return activeQueueManager.checkActive(token);

    }

    @Override
    public void expireTokens(LocalDateTime now) {
        activeQueueManager.expireTokens(now);
    }
}
