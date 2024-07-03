package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.TokenStatus;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QueueJedisTest {

    @Autowired
    QueueManager queueManager;

    Token createToken(long userId, String tokenClaim){
        long concertId = 1;
        return new Token(concertId, tokenClaim, userId, TokenStatus.WAITING, LocalDateTime.now());
    }
    @Test
    @DisplayName("대기열에 토큰을 등록하고 대기열 정보를 받는다.")
    void register_token_to_waiting_queue() throws InterruptedException {
        long userId1=1;
        Token token1 = createToken(userId1, "FAKE-TOKEN1");
        queueManager.insertInWaitingQueue(token1);

        WaitingInfo waitingInfo1 = queueManager.getWaitingInfoByToken(token1);

        assertThat(waitingInfo1.userId()).isEqualTo(userId1);
        assertThat(waitingInfo1.waitingNo()).isEqualTo(1);

        long userId2=2;
        Token token2 = createToken(userId2, "FAKE-TOKEN2");
        queueManager.insertInWaitingQueue(token2);

        WaitingInfo waitingInfo2 = queueManager.getWaitingInfoByToken(token1);

        assertThat(waitingInfo2.userId()).isEqualTo(userId2);
        assertThat(waitingInfo2.waitingNo()).isEqualTo(2);


    }


}
