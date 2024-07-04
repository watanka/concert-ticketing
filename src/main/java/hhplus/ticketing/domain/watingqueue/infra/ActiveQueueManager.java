package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

import java.time.LocalDateTime;
import java.util.Map;

public interface ActiveQueueManager {

    void activate(Token token, LocalDateTime activatedTime);

    void deactivate(Token token);

    boolean checkExpired(Token token, LocalDateTime now);

    void activate(Map.Entry<String, WaitingInfo> entry, LocalDateTime now);

    boolean checkActive(Token token);

    void expireTokens(LocalDateTime now);

    boolean isExpired(Token token);
}
