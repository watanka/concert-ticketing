package hhplus.ticketing.domain.token.repository;

import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;

import java.util.List;

public interface WaitingQueueManager {

    WaitingInfo getWaitingInfoByToken(Token token);
    WaitingInfo insertInWaitingQueue(Token token);
    void deleteFromWaitingQueue(Token token);

    List<String> getTokensInWaitingQueue(long concertId);

    List<String> getNTokensByTimeOrder(long concertId, int n);

    void remove(Token token);

    void clear();

    void removeByClaim(long concertId, String tokenClaim);
}
