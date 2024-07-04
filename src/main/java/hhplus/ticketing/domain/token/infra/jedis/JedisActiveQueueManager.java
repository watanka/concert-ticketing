package hhplus.ticketing.domain.token.infra.jedis;

import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.repository.ActiveTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JedisActiveQueueManager implements ActiveTokenManager {

    private final long ACTIVE_TIME = 5 * 60;
    private static final String ACTIVATED_KEYNAME = "ACTIVATED:CONCERT_ID";
    private final JedisPooled jedis;

    @Override
    public void activate(Token token) {
        String keyName = ACTIVATED_KEYNAME + token.getConcertId();
        jedis.zadd(keyName, token.getTimeScale(), token.getClaim());
    }

    @Override
    public void deactivate(Token token) {
        String keyName = ACTIVATED_KEYNAME + token.getConcertId();
        jedis.zrem(keyName, token.getClaim());
    }


    public List<String> getActivatedTokens(long concertId) {
        return jedis.zrange(ACTIVATED_KEYNAME + concertId, 0, -1);
    }

    @Override
    public boolean checkActive(Token token) {
        return jedis.zscore(ACTIVATED_KEYNAME + token.getConcertId(), token.getClaim()) != null;
    }

    @Override
    public void expireTokens(long concertId, LocalDateTime now) {

        long expireThresholdTime = now.minusSeconds(ACTIVE_TIME).toInstant(ZoneOffset.UTC).toEpochMilli();

        jedis.zremrangeByScore(ACTIVATED_KEYNAME+concertId, "(" + expireThresholdTime, "+inf");
    }

    @Override
    public boolean checkExpired(Token token) {
        return jedis.zscore(ACTIVATED_KEYNAME + token.getConcertId(), token.getClaim()) == null;
    }

    @Override
    public void clear() {
        Set<String> keys = jedis.keys(ACTIVATED_KEYNAME+"*");

        for (String key : keys) {
            // 각 키의 타입을 확인
            String type = jedis.type(key);

            // 키가 sorted set인 경우 삭제
            if ("zset".equals(type)) {
                jedis.del(key);
            }
        }
    }
}
