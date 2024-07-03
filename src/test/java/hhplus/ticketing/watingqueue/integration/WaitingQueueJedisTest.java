package hhplus.ticketing.watingqueue.integration;

import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.watingqueue.components.TokenGenerator;
import hhplus.ticketing.domain.watingqueue.components.QueueManager;
import hhplus.ticketing.domain.watingqueue.components.WaitingQueueService;
import hhplus.ticketing.domain.watingqueue.infra.MemoryTokenGenerator;
import hhplus.ticketing.domain.watingqueue.infra.MemoryQueueManager;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.TokenStatus;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WaitingQueueJedisTest {

    TokenGenerator tokenGenerator = new MemoryTokenGenerator();
    QueueManager queueManager = new MemoryQueueManager();
    WaitingQueueService waitingQueueService = new WaitingQueueService(tokenGenerator, queueManager);

    @Test
    @DisplayName("없는 토큰을 조회할 경우 예외 처리")
    void query_invalid_token(){
        Token invalidToken = new Token(1, "INVALID-TOKEN", 1, TokenStatus.WAITING, LocalDateTime.now());


        assertThrows(InvalidTokenException.class, () ->
                waitingQueueService.query(invalidToken));

    }

    @Test
    @DisplayName("유저ID로 대기열에 등록하면 토큰을 받는다.")
    void receive_token_when_register_in_waiting_queue_with_userID(){
        long userId = 1;

        Token token = waitingQueueService.register(1, userId);
        WaitingInfo waitingInfo = waitingQueueService.query(token);

        assertThat(waitingInfo.userId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("토큰으로 대기열을 조회하면, 현재 대기순서를 알 수 있다.")
    void query_waiting_number_by_token(){
        long userId1 = 100;
        long userId2 = 200;

        Token token1 = waitingQueueService.register(1, userId1);
        Token trgToken = waitingQueueService.register(1, userId2);

        WaitingInfo info = waitingQueueService.query(trgToken);

        assertThat(info.waitingNo()).isEqualTo(2);
    }

    @Test
    @DisplayName("입장 순서가 되면 토큰을 활성화시킨다.")
    void activate_token_when_waiting_is_over(){
        QueueManager queueManager = new MemoryQueueManager();

        long userId = 1;
        Token token = waitingQueueService.register(1, userId);

        assertThat(token.getStatus()).isEqualTo(TokenStatus.WAITING);

        queueManager.activate(token);

        assertThat(token.getStatus()).isEqualTo(TokenStatus.ACTIVE);
    }

    @Test
    @DisplayName("토큰 유효시간이 지나면 만료시킨다.")
    void deactivate_token_when_time_up(){
        QueueManager queueManager = new MemoryQueueManager();

        LocalDateTime timeAlreadyExpired = LocalDateTime.now().minusMinutes(10);


        Token token = new Token(1, "", 1, TokenStatus.ACTIVE, timeAlreadyExpired);
        token.updateStatus(TokenStatus.WAITING);


        assertThat(queueManager.checkExpired(token)).isTrue();

        queueManager.deactivate(token);

        assertThat(token.getStatus()).isEqualTo(TokenStatus.EXPIRED);


    }
}
