package hhplus.ticketing.domain.token.components;


import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.token.infra.QueueManager;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    @Autowired
    private final TokenGenerator tokenGenerator;
    @Autowired
    private final QueueManager queueManager;

    public WaitingInfo query(Token token) {
        WaitingInfo waitingInfo = queueManager.getWaitingInfoByToken(token);

        if (waitingInfo == null){
            throw new InvalidTokenException();
        }
        return waitingInfo;

    }

    public Token register(long concertId, long userId, LocalDateTime issuedAt) {

        Token token = tokenGenerator.issue(concertId, userId, issuedAt);
        queueManager.insertInWaitingQueue(token);
        return token;
    }


}
