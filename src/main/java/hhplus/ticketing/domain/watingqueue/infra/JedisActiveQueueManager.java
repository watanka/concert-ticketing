package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

import java.time.LocalDateTime;
import java.util.Map;

public class JedisActiveQueueManager implements ActiveQueueManager{
    @Override
    public void activate(Token token, LocalDateTime activatedTime) {

    }

    @Override
    public void deactivate(Token token) {

    }

    @Override
    public boolean checkExpired(Token token, LocalDateTime now) {
        return false;
    }

    @Override
    public void activate(Map.Entry<String, WaitingInfo> entry, LocalDateTime now) {

    }

    @Override
    public boolean checkActive(Token token) {
        return false;
    }

    @Override
    public void expireTokens(LocalDateTime now) {

    }

    @Override
    public boolean isExpired(Token token) {
        return false;
    }
}
