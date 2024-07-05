package hhplus.ticketing.domain.token.infra.queue;

import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import hhplus.ticketing.domain.token.repository.ActiveTokenManager;
import hhplus.ticketing.domain.token.repository.WaitingQueueManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueManager {
    @Autowired
    private final WaitingQueueManager waitingQueueManager;
    @Autowired
    private final ActiveTokenManager activeQueueManager;


    public WaitingInfo getWaitingInfoByToken(Token token) throws InvalidTokenException {
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
