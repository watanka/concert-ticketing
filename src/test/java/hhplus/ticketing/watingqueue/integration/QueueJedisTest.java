package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.TokenStatus;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QueueJedisTest {

    @Autowired
    QueueManager queueManager;


    @BeforeEach
    void setUp(){
        queueManager.deleteAll();
    }

    Token createToken(long userId, long concertId, String tokenClaim, LocalDateTime registerTime){
        return new Token(concertId, tokenClaim, userId, TokenStatus.WAITING, registerTime);
    }
    @Test
    @DisplayName("대기열에 토큰을 등록하고 대기열 정보를 받는다.")
    void register_token_to_waiting_queue() throws InterruptedException {
        long concertId = 2L;
        long userId1=1;
        Token token1 = createToken(userId1, concertId, "FAKE-TOKEN1", LocalDateTime.now().plusSeconds(10));
        queueManager.insertInWaitingQueue(token1);

        WaitingInfo waitingInfo1 = queueManager.getWaitingInfoByToken(token1);

        assertThat(waitingInfo1.userId()).isEqualTo(userId1);
        assertThat(waitingInfo1.waitingNo()).isEqualTo(1);

        long userId2=2;
        Token token2 = createToken(userId2, concertId, "FAKE-TOKEN2", LocalDateTime.now().plusSeconds(50));
        queueManager.insertInWaitingQueue(token2);

        WaitingInfo waitingInfo2 = queueManager.getWaitingInfoByToken(token2);

        assertThat(waitingInfo2.userId()).isEqualTo(userId2);
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

        List<String> tokensInWaitingQueue = queueManager.getTokensInWaitingQueue(concertId);
        List<String> activatedTokens = queueManager.getActivatedTokens(concertId);

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
