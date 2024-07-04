package hhplus.ticketing.domain.watingqueue.components;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueManager {

    boolean exists(String keyName, Token token);

    WaitingInfo getWaitingInfoByToken(Token token);

    WaitingInfo insertInWaitingQueue(Token token);

    void activate(Token token, LocalDateTime dateTime);
    void deactivate(Token token);
    boolean checkExpired(Token token, LocalDateTime now);

    void deleteAll();

    void activateTokensByTimeOrder(long concertId, int n);

    List<String> getTokensInWaitingQueue(long concertId);

    List<String> getActivatedTokens(long concertId);

    boolean checkActive(Token token);

    void expireTokens(LocalDateTime now);
}
