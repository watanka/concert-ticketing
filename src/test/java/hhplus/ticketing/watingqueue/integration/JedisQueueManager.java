package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;

@Repository
@RequiredArgsConstructor
public class JedisQueueManager implements QueueManager {


    private final JedisPooled jedis;
    private static final String REDIS_KEY_NAME = "CONCERT:";


    public long getWaitingNo(String keyName, Token token){
        return jedis.zrank(keyName, token.getClaim());
    }

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        String keyName = REDIS_KEY_NAME + token.getConcertId();
        long waitingNo = getWaitingNo(keyName, token);

        return new WaitingInfo(token.getUserId(), waitingNo);
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        String keyName = REDIS_KEY_NAME + token.getConcertId();

        long waitingNo = jedis.zadd(keyName, token.getTimeScale(), token.getClaim());
        return new WaitingInfo(token.getUserId(), waitingNo + 1);
    }




    @Override
    public void deleteFromWaitingQueue(Token token) {

    }

    @Override
    public void activate(Token token) {

    }

    @Override
    public void deactivate(Token token) {

    }

    @Override
    public boolean checkExpired(Token token) {
        return false;
    }
}
