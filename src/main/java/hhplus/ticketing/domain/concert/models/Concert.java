package hhplus.ticketing.domain.concert.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Concert {
    public long id;
    public String name;
    public String performerName;

    public Concert(String name, String performerName) {
        this.name = name;
        this.performerName = performerName;
    }
}
