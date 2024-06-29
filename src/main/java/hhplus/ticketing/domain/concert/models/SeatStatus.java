package hhplus.ticketing.domain.concert.models;

public enum SeatStatus {
    AVAILABLE(true), RESERVED(false);

    SeatStatus(boolean isAvailable) {
    }

    public static SeatStatus to(boolean isAvailable) {
        if (isAvailable) return AVAILABLE;
        else return RESERVED;
    }

    public boolean toBool() {
        return this == AVAILABLE;
    }
}
