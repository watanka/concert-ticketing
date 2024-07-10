package hhplus.ticketing.domain.token.infra.queue.fake;

import hhplus.ticketing.domain.token.repository.WaitingQueueManager;
import hhplus.ticketing.domain.token.models.Token;
import hhplus.ticketing.domain.token.models.WaitingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryWaitingQueueManager implements WaitingQueueManager {
    Map<String, WaitingInfo> tokenMap = new HashMap<>();

    @Override
    public WaitingInfo getWaitingInfoByToken(Token token) {
        return tokenMap.get(token.getJwt());
    }

    @Override
    public WaitingInfo insertInWaitingQueue(Token token) {
        WaitingInfo waitingInfo = new WaitingInfo(tokenMap.size()+1, token.getIssuedAt());
        tokenMap.put(token.getJwt(), waitingInfo);
        return waitingInfo;
    }

    @Override
    public void deleteFromWaitingQueue(Token token) {
        tokenMap.remove(token.getJwt());
    }

    @Override
    public List<String> getTokensInWaitingQueue(long concertId) {
        return null;
    }

    @Override
    public List<String> getNTokensByTimeOrder(long concertId, int n) {
        List<Map.Entry<String, WaitingInfo>> nTokensInOrder = tokenMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(n)
                .toList();

        List<String> tokenClaimToBeActiveList = new ArrayList<>();
        for (Map.Entry<String, WaitingInfo> entry : nTokensInOrder) {
            String claim = entry.getKey(); // 원하는 포맷으로 변환
            tokenClaimToBeActiveList.add(claim);
        }
        return tokenClaimToBeActiveList;

    }

    @Override
    public void remove(Token token) {
        tokenMap.remove(token.getJwt());
    }

    @Override
    public void clear() {
        tokenMap.clear();
    }

    @Override
    public void removeByClaim(long concertId, String tokenClaim) {
        tokenMap.remove(tokenClaim);
    }
}
