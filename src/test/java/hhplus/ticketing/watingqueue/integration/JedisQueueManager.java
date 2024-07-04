package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.infra.ActiveQueueManager;
import hhplus.ticketing.domain.watingqueue.infra.WaitingQueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JedisQueueManager implements QueueManager {

    private final long ACTIVE_TIME= 5 * 60;
    private final JedisPooled jedis;

    private static final String ACTIVATED_KEYNAME = "ACTIVATED:CONCERT_ID";


    private final WaitingQueueManager waitingQueueManager;
    private final ActiveQueueManager activeQueueManager;



    @Override
    public boolean exists(String keyName, Token token){
        return jedis.zscore(keyName, token.getClaim()) != null;
    }

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        return waitingQueueManager.getWaitingInfoByToken(token);
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        return waitingQueueManager.insertInWaitingQueue(token);
    }


    @Override
    public void activate(Token token, LocalDateTime activatedTime) {
        String keyName = ACTIVATED_KEYNAME + token.getConcertId();
        jedis.zadd(keyName, token.getTimeScale(), token.getClaim());
    }

    @Override
    public void deactivate(Token token) {
        String keyName = ACTIVATED_KEYNAME + token.getConcertId();
        jedis.zrem(keyName, token.getClaim());
    }

    @Override
    public boolean checkExpired(Token token, LocalDateTime now) {
        String keyName = ACTIVATED_KEYNAME + token.getConcertId();

        Double issuedAt = jedis.zscore(keyName, token.getClaim());

        long nowTimeScale = now.toInstant(ZoneOffset.UTC).toEpochMilli(); // token의 toTimeScale과 합쳐야함.

        return nowTimeScale < issuedAt + ACTIVE_TIME * 1000 ;
    }

    @Override
    public void deleteAll() {
        Set<String> keys = jedis.keys("*");

        for (String key : keys) {
            // 각 키의 타입을 확인
            String type = jedis.type(key);

            // 키가 sorted set인 경우 삭제
            if ("zset".equals(type)) {
                jedis.del(key);
            }
        }
    }

    @Override
    public void activateTokensByTimeOrder(long concertId, int n) {


        List<String> tokensByOrder = waitingQueueManager.getNTokensByTimeOrder(concertId, n);
        if (!tokensByOrder.isEmpty()) {
            // remove from waiting queue
            for (String tokenClaim : tokensByOrder) {
                waitingQueueManager.removeByClaim(concertId, tokenClaim);
            }

        }

        // put int activated token list
        for (String tokenClaim : tokensByOrder) {
            Token tokenTobeActivated = new Token(concertId, tokenClaim, LocalDateTime.now());
            activate(tokenTobeActivated, LocalDateTime.now());
        }



    }

    @Override
    public List<String> getTokensInWaitingQueue(long concertId) {
        return waitingQueueManager.getTokensInWaitingQueue(concertId);
    }

    @Override
    public List<String> getActivatedTokens(long concertId) {
        return jedis.zrange(ACTIVATED_KEYNAME + concertId, 0, -1);
    }

    @Override
    public boolean checkActive(Token token) {
        return exists(ACTIVATED_KEYNAME + token.getConcertId(), token);
    }

    @Override
    public void expireTokens(LocalDateTime now) {

    }

}
