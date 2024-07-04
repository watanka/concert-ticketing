package hhplus.ticketing.domain.watingqueue.components;


import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import hhplus.ticketing.domain.watingqueue.repository.TokenRepository;
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
