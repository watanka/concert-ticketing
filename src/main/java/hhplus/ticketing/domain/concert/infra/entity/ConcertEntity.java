package hhplus.ticketing.domain.concert.infra.entity;

import hhplus.ticketing.domain.concert.models.Concert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    String name;


    @Column(name="performer_name")
    String performerName;

    @Builder
    public ConcertEntity(String name, String performerName) {
        this.name = name;
        this.performerName = performerName;
    }

    public static ConcertEntity from(Concert concert) {
        return ConcertEntity.builder()
                .name(concert.getName())
                .performerName(concert.getPerformerName())
                .build();
    }

    public static Concert to(ConcertEntity concertEntity) {
        return Concert.builder()
                .name(concertEntity.getName())
                .performerName(concertEntity.getPerformerName())
                .build();
    }
}
