package hhplus.ticketing.domain.watingqueue.models;

import java.time.LocalDateTime;

public record WaitingInfo(
        long userId,
        long waitingNo,
        LocalDateTime issuedAt
) implements Comparable<WaitingInfo>{

    @Override
    public int compareTo(WaitingInfo other) {
        return this.issuedAt.compareTo(other.issuedAt);
    }
}
