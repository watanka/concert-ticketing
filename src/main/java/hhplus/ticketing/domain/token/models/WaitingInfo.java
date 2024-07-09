package hhplus.ticketing.domain.token.models;

import java.time.LocalDateTime;

public record WaitingInfo(
        long waitingNo,
        LocalDateTime issuedAt
) implements Comparable<WaitingInfo> {

    @Override
    public int compareTo(WaitingInfo other) {
        return this.issuedAt.compareTo(other.issuedAt);
    }
}
