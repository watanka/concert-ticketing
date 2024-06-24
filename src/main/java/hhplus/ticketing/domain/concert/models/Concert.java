package hhplus.ticketing.domain.concert.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Concert {
    public long concertId;
    public String concertName;
    public String performerName;


}
