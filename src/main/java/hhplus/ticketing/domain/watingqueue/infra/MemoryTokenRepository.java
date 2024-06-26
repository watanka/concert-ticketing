package hhplus.ticketing.domain.watingqueue.infra;

import hhplus.ticketing.domain.watingqueue.models.Token;
import hhplus.ticketing.domain.watingqueue.models.WaitingInfo;
import hhplus.ticketing.domain.watingqueue.repository.TokenRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryTokenRepository implements TokenRepository {

    Map<Long, WaitingInfo> tokenMap = new HashMap<>();



    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        return tokenMap.get(token.getUserId());
    }

    @Override
    public WaitingInfo save(Token token) {
        WaitingInfo waitingInfo = new WaitingInfo(token.getUserId(), tokenMap.size()+1);
        tokenMap.put(token.getUserId(), waitingInfo);
        return waitingInfo;
    }


}
