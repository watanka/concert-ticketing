package hhplus.ticketing.domain.watingqueue.components;


import hhplus.ticketing.base.exceptions.InvalidTokenException;
import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import hhplus.ticketing.domain.watingqueue.repository.TokenRepository;

public class WaitingQueueService {

    TokenGenerator tokenGenerator;
    TokenRepository tokenRepository;

    public WaitingQueueService(TokenGenerator tokenGenerator, TokenRepository tokenRepository) {
        this.tokenGenerator = tokenGenerator;
        this.tokenRepository = tokenRepository;
    }


    public WaitingInfo query(Token token) {
        WaitingInfo waitingInfo = tokenRepository.getWaitingInfoByToken(token);

        if (waitingInfo == null){
            throw new InvalidTokenException();
        }
        return waitingInfo;

    }

    public Token register(long userId) {

        Token token = tokenGenerator.issue(userId);
        tokenRepository.save(token);
        return token;
    }


}
