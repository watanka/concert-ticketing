package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JedisQueueManager implements QueueManager {


    private final JedisPooled jedis;
    private static final String WAITING_KEYNAME = "WAITINGQUEUE:CONCERT_ID";
    private static final String ACTIVATED_KEYNAME = "ACTIVATED:CONCERT_ID";



    public long getWaitingNo(String keyName, Token token){
        return jedis.zrank(keyName, token.getClaim()) + 1;
    }

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        String keyName = WAITING_KEYNAME + token.getConcertId();
        long waitingNo = getWaitingNo(keyName, token);

        return new WaitingInfo(token.getUserId(), waitingNo);
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        String keyName = WAITING_KEYNAME + token.getConcertId();

        jedis.zadd(keyName, token.getTimeScale(), token.getClaim());
        return new WaitingInfo(token.getUserId(), getWaitingNo(keyName, token));
    }




    @Override
    public void deleteFromWaitingQueue(Token token) {

    }

    @Override
    public void activate(Token token) {
        String keyName = ACTIVATED_KEYNAME + token.getConcertId();
        jedis.zadd(keyName, token.getTimeScale(), token.getClaim());
    }

    @Override
    public void deactivate(Token token) {

    }

    @Override
    public boolean checkExpired(Token token) {
        return false;
    }

    @Override
    public void deleteAll(String keyName) {
        jedis.del(keyName);
    }

    @Override
    public void activateTokensByTimeOrder(long concertId, int n) {
        String keyName = WAITING_KEYNAME + concertId;
        List<String> tokensByOrder = jedis.zrange(keyName, 0, n-1);
        if (!tokensByOrder.isEmpty()) {
            jedis.zrem(keyName, tokensByOrder.toArray(new String[0]));
        }

        for (String tokenClaim : tokensByOrder) {
            Token tokenTobeActivated = new Token(concertId, tokenClaim, LocalDateTime.now());
            activate(tokenTobeActivated);
        }



    }

    @Override
    public List<String> getTokensInWaitingQueue(long concertId) {
        return jedis.zrange(WAITING_KEYNAME + concertId, 0, -1);
    }

    @Override
    public List<String> getActivatedTokens(long concertId) {
        return jedis.zrange(ACTIVATED_KEYNAME + concertId, 0, -1);
    }
}
