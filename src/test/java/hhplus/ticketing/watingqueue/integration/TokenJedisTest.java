package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.components.WaitingQueueService;
import hhplus.ticketing.domain.token.infra.queue.jedis.JedisActiveQueueManager;
import hhplus.ticketing.domain.token.infra.queue.jedis.JedisWaitingQueueManager;
import hhplus.ticketing.domain.token.infra.queue.QueueManager;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TokenJedisTest {

    @Autowired
    QueueManager queueManager;

    @Autowired
    JedisWaitingQueueManager jedisWaitingQueueManager;
    @Autowired
    JedisActiveQueueManager jedisActiveQueueManager;

    @Autowired
    WaitingQueueService waitingQueueService;

    @BeforeEach
    void setUp(){
        queueManager.deleteAll();
    }

    Token createToken(long userId, long concertId, String tokenClaim, LocalDateTime registerTime){
        return new Token(concertId, tokenClaim, userId, registerTime);
    }

    @Test
    @DisplayName("없는 토큰을 조회할 경우 예외 처리")
    void query_invalid_token(){
        Token invalidToken = new Token(1, "INVALID-TOKEN",  LocalDateTime.now());


        assertThrows(InvalidTokenException.class, () ->
                waitingQueueService.query(invalidToken));

    }

    @Test
    @DisplayName("유저ID로 대기열에 등록하면 토큰을 받는다.")
    void receive_token_when_register_in_waiting_queue_with_userID(){
        long userId = 1;

        Token token = waitingQueueService.register(1, userId, LocalDateTime.now());
        WaitingInfo waitingInfo = waitingQueueService.query(token);

        assertThat(waitingInfo.waitingNo()).isEqualTo(1);
    }

    @Test
    @DisplayName("토큰으로 대기열을 조회하면, 현재 대기순서를 알 수 있다.")
    void query_waiting_number_by_token(){
        long userId1 = 100;
        long userId2 = 200;

        Token token1 = waitingQueueService.register(1, userId1, LocalDateTime.now());
        Token trgToken = waitingQueueService.register(1, userId2, LocalDateTime.now());

        WaitingInfo info = waitingQueueService.query(trgToken);

        assertThat(info.waitingNo()).isEqualTo(2);
    }

    @Test
    @DisplayName("입장 순서가 되면 토큰을 활성화시킨다.")
    void activate_token_when_waiting_is_over(){

        long userId = 1;
        Token token = waitingQueueService.register(1, userId, LocalDateTime.now());

        assertThat(queueManager.checkActive(token)).isFalse();

        queueManager.activateTokensByTimeOrder(token.getConcertId(), 1);

        assertThat(queueManager.checkActive(token)).isTrue();
    }

    @Test
    @DisplayName("토큰 유효시간이 지나면 만료시킨다.")
    void deactivate_token_when_time_up(){

        int n = 1;
        long concertId = 1L;
        long userId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime supposeExpiredTime = issuedAt.minusMinutes(10);

        Token token = waitingQueueService.register(concertId, userId, issuedAt);

        queueManager.activateTokensByTimeOrder(concertId, n);
        assertThat(queueManager.checkActive(token)).isTrue();

        queueManager.expireTokensInActiveQueue(concertId, supposeExpiredTime);
        assertThat(queueManager.checkExpired(token)).isTrue();
    }

    @Test
    @DisplayName("대기열에 토큰을 등록하고 대기열 정보를 받는다.")
    void register_token_to_waiting_queue() throws InterruptedException {
        long concertId = 2L;
        long userId1=1;
        Token token1 = createToken(userId1, concertId, "FAKE-TOKEN1", LocalDateTime.now().plusSeconds(10));
        queueManager.insertInWaitingQueue(token1);

        WaitingInfo waitingInfo1 = queueManager.getWaitingInfoByToken(token1);

        assertThat(waitingInfo1.waitingNo()).isEqualTo(1);

        long userId2=2;
        Token token2 = createToken(userId2, concertId, "FAKE-TOKEN2", LocalDateTime.now().plusSeconds(50));
        queueManager.insertInWaitingQueue(token2);

        WaitingInfo waitingInfo2 = queueManager.getWaitingInfoByToken(token2);

        assertThat(waitingInfo2.waitingNo()).isEqualTo(2);
    }

    @Test
    @DisplayName("토큰을 시간순서대로 n개씩 활성화시킨다. 활성화된 토큰은 대기열에서 제거된다.")
    void activate_n_tokens_by_time_order_and_removed_from_waiting_queue() {
        long concertId = 3L;
        int n = 50;
        int totalTokenNum = 80;
        LocalDateTime time = LocalDateTime.now();
        Token token = null;
        for (int i = 0; i < totalTokenNum; i++) {
            token = createToken(i, concertId, "FAKE-TOKEN" + i, time.plusSeconds(i));
            queueManager.insertInWaitingQueue(token);
        }

        queueManager.activateTokensByTimeOrder(concertId, n);

        List<String> tokensInWaitingQueue = jedisWaitingQueueManager.getTokensInWaitingQueue(concertId);
        List<String> activatedTokens = jedisActiveQueueManager.getActivatedTokens(concertId);

        assertThat(tokensInWaitingQueue).hasSize(totalTokenNum - n);
        assertThat(activatedTokens).hasSize(n);


    }

    @Test
    @DisplayName("토큰이 활성화되어있는지 체크한다.")
    void check_if_token_is_active(){
        long userId = 1;
        long concertId = 1;
        Token token = createToken(userId, concertId, "FAKE-TOKEN", LocalDateTime.now());
        queueManager.insertInWaitingQueue(token);
        queueManager.activateTokensByTimeOrder(concertId, 1);

        assertThat(queueManager.checkActive(token)).isTrue();

    }



}
