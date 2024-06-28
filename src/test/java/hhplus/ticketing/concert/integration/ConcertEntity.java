package hhplus.ticketing.concert.integration;

import jakarta.persistence.*;
import org.mozilla.javascript.annotations.JSGetter;

@Entity
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    String concertName;


    @Column(name="performer_name")
    String performerName;

    public ConcertEntity(String concertName, String performerName) {

    }
}
