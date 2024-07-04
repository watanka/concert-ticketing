package hhplus.ticketing.domain.token.infra.jedis;

import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import hhplus.ticketing.domain.token.repository.WaitingQueueManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JedisWaitingQueueManager implements WaitingQueueManager {


    private final JedisPooled jedis;
    private static final String WAITING_KEYNAME = "WAITINGQUEUE:CONCERT_ID";

    public boolean exists(String keyName, Token token){
        return jedis.zscore(keyName, token.getClaim()) != null;
    }

    public long getWaitingNo(String keyName, Token token){
        return jedis.zrank(keyName, token.getClaim()) + 1;
    }

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        String keyName = WAITING_KEYNAME + token.getConcertId();
        if (!exists(keyName, token)){
            throw new InvalidTokenException();
        }
        long waitingNo = getWaitingNo(keyName, token);

        return new WaitingInfo(token.getUserId(), waitingNo, token.getIssuedAt());
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        String keyName = WAITING_KEYNAME + token.getConcertId();

        jedis.zadd(keyName, token.getTimeScale(), token.getClaim());
        return new WaitingInfo(token.getUserId(), getWaitingNo(keyName, token), token.getIssuedAt());
    }

    @Override
    public void deleteFromWaitingQueue(Token token) {
        jedis.zrem(WAITING_KEYNAME + token.getConcertId(), token.getClaim());
    }

    @Override
    public List<String> getTokensInWaitingQueue(long concertId) {
        return jedis.zrange(WAITING_KEYNAME + concertId, 0, -1);
    }


    @Override
    public List<String> getNTokensByTimeOrder(long concertId, int n) {
        return jedis.zrange(WAITING_KEYNAME + concertId, 0, n-1);
    }

    @Override
    public void remove(Token token) {
        jedis.zrem(WAITING_KEYNAME + token.getConcertId(), token.getClaim());
    }

    @Override
    public void clear() {
        Set<String> keys = jedis.keys(WAITING_KEYNAME+"*");

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
    public void removeByClaim(long concertId, String tokenClaim) {
        jedis.zrem(WAITING_KEYNAME + concertId, tokenClaim);
    }
}
