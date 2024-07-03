package hhplus.ticketing.domain.watingqueue.repository;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;

public interface TokenRepository {
    WaitingInfo getWaitingInfoByToken(Token token);

    WaitingInfo save(Token token);
}
