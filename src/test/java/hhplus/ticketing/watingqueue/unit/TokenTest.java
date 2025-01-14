package hhplus.ticketing.watingqueue.unit;

import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.components.*;
import hhplus.ticketing.domain.token.infra.queue.fake.MemoryActiveQueueManager;
import hhplus.ticketing.domain.token.infra.token.JwtTokenManager;
import hhplus.ticketing.domain.token.infra.queue.fake.MemoryWaitingQueueManager;
import hhplus.ticketing.domain.token.repository.ActiveTokenManager;
import hhplus.ticketing.domain.token.infra.queue.QueueManager;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import hhplus.ticketing.domain.token.repository.TokenManager;
import hhplus.ticketing.domain.token.repository.WaitingQueueManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenTest {


    TokenManager tokenManager = new JwtTokenManager();
    WaitingQueueManager waitingQueueManager = new MemoryWaitingQueueManager();
    ActiveTokenManager activeQueueManager = new MemoryActiveQueueManager();


    QueueManager queueManager = new QueueManager(waitingQueueManager, activeQueueManager);
    WaitingQueueService waitingQueueService = new WaitingQueueService(tokenManager, queueManager);

    @Test
    @DisplayName("없는 토큰을 조회할 경우 예외 처리")
    void query_invalid_token(){
        Token invalidToken = new Token(1, "INVALID-TOKEN", LocalDateTime.now());
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
    @DisplayName("입장 순서가 되면 토큰을 활성화시킨다. 활성화 여부는 토큰의 활성토큰리스트 포함 여부이다.")
    void activate_token_when_waiting_is_over(){
        long userId = 1;
        Token token = waitingQueueService.register(1, userId, LocalDateTime.now());

        assertThat(queueManager.getWaitingInfoByToken(token).waitingNo()).isNotNull();

        queueManager.activateTokensByTimeOrder(token.getConcertId(), 1);

        assertThat(queueManager.checkActive(token)).isTrue();
    }

    @Test
    @DisplayName("토큰 유효시간이 지나면 만료시킨다.")
    void deactivate_token_when_time_up(){

        long concertId = 1L;
        long userId = 1L;

        LocalDateTime issuedAt = LocalDateTime.now();

        Token token = waitingQueueService.register(1, userId, LocalDateTime.now());
        queueManager.activateTokensByTimeOrder(concertId, 1);
        assertThat(queueManager.checkActive(token)).isTrue();

        queueManager.expireTokensInActiveQueue(concertId, issuedAt.plusMinutes(10));
        assertThat(queueManager.checkExpired(token)).isTrue();
    }
}
