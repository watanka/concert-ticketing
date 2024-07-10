package hhplus.ticketing.domain.token.components;


import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.infra.queue.QueueManager;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import hhplus.ticketing.domain.token.repository.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    @Autowired
    private final TokenManager tokenManager;
    @Autowired
    private final QueueManager queueManager;

    public WaitingInfo query(Token token) throws InvalidTokenException {
        WaitingInfo waitingInfo = queueManager.getWaitingInfoByToken(token);
        if (waitingInfo == null){
            throw new InvalidTokenException();
        }
        return waitingInfo;
    }

    public Token register(long concertId, long userId, LocalDateTime issuedAt) {
        Token token = tokenManager.issue(concertId, userId, issuedAt);
        queueManager.insertInWaitingQueue(token);
        return token;
    }


    public void validate(Token token) throws InvalidTokenException {
        boolean isValid = tokenManager.validate(token);
        boolean isActive = queueManager.checkActive(token);
        if (!isValid || !isActive) {
            throw new InvalidTokenException();
        }

    }
}
