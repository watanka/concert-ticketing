package hhplus.ticketing.domain.token.infra.queue.fake;

import hhplus.ticketing.domain.token.repository.WaitingQueueManager;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import hhplus.ticketing.domain.token.repository.ActiveTokenManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class MemoryQueueManager{


    private final WaitingQueueManager waitingQueueManager;
    private final ActiveTokenManager activeQueueManager;


    public WaitingInfo getWaitingInfoByToken(Token token) {
        return waitingQueueManager.getWaitingInfoByToken(token);
    }


    public WaitingInfo insertInWaitingQueue(Token token) {
        return waitingQueueManager.insertInWaitingQueue(token);
    }



    public boolean checkExpired(Token token) {
        return activeQueueManager.checkExpired(token);
    }


    public void deleteAll() {
        waitingQueueManager.clear();
        activeQueueManager.clear();
    }


    public void activateTokensByTimeOrder(long concertId, int n) {

        List<String> sortedNWaitingInfo = waitingQueueManager.getNTokensByTimeOrder(concertId, n);

        for (String claim : sortedNWaitingInfo) {
            LocalDateTime now = LocalDateTime.now();
            Token newToken = new Token(concertId, claim, now); // 임시 우회. activeQueueManager 인터페이스 맞추기 위해서...

            waitingQueueManager.remove(newToken);
            activeQueueManager.activate(newToken);
        }
    }

    public boolean checkActive(Token token) {
        return activeQueueManager.checkActive(token);

    }

    public void expireTokensInActiveQueue(long concertId, LocalDateTime now) {
        activeQueueManager.expireTokens(concertId, now);
    }
}
