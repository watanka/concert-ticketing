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
import static org.assertj.core.api.InstanceOfAssertFactories.iterable;

@SpringBootTest
public class QueueJedisTest {

    @Autowired
    QueueManager queueManager;

    long concertId = 1;

    @BeforeEach
    void setUp(){
        queueManager.deleteAll("CONCERT:"+concertId);
    }

    Token createToken(long userId, String tokenClaim, LocalDateTime registerTime){
        return new Token(concertId, tokenClaim, userId, TokenStatus.WAITING, registerTime);
    }
    @Test
    @DisplayName("대기열에 토큰을 등록하고 대기열 정보를 받는다.")
    void register_token_to_waiting_queue() throws InterruptedException {
        long userId1=1;
        Token token1 = createToken(userId1, "FAKE-TOKEN1", LocalDateTime.now().plusSeconds(10));
        queueManager.insertInWaitingQueue(token1);

        WaitingInfo waitingInfo1 = queueManager.getWaitingInfoByToken(token1);

        assertThat(waitingInfo1.userId()).isEqualTo(userId1);
        assertThat(waitingInfo1.waitingNo()).isEqualTo(1);

        long userId2=2;
        Token token2 = createToken(userId2, "FAKE-TOKEN2", LocalDateTime.now().plusSeconds(50));
        queueManager.insertInWaitingQueue(token2);

        WaitingInfo waitingInfo2 = queueManager.getWaitingInfoByToken(token2);

        assertThat(waitingInfo2.userId()).isEqualTo(userId2);
        assertThat(waitingInfo2.waitingNo()).isEqualTo(2);
    }

    @Test
    @DisplayName("토큰을 시간순서대로 n개씩 활성화시킨다.")
    void activate_n_tokens_by_time_order() {
        int n = 50;
        int totalTokenNum = 80;
        LocalDateTime time = LocalDateTime.now();
        Token token = null;
        for (int i = 0; i < totalTokenNum; i++) {
            token = createToken(i, "FAKE-TOKEN" + i, time.plusSeconds(i));
            queueManager.insertInWaitingQueue(token);
        }

        queueManager.activateTokensByTimeOrder(concertId, n);

        List<String> tokensInWaitingQueue = queueManager.getTokensInWaitingQueue(concertId);
        List<String> activatedTokens = queueManager.getActivatedTokens(concertId);

        assertThat(tokensInWaitingQueue).hasSize(totalTokenNum - n);
        assertThat(activatedTokens).hasSize(n);


    }


}
