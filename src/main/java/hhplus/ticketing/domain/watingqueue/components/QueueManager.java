package hhplus.ticketing.domain.watingqueue.components;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

public interface QueueManager {

    WaitingInfo getWaitingInfoByToken(Token token);

    WaitingInfo insertInWaitingQueue(Token token);

    void deleteFromWaitingQueue(Token token);

    void activate(Token token);
    void deactivate(Token token);
    boolean checkExpired(Token token);
}
