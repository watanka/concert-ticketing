package hhplus.ticketing.domain.concert.infra.entity;

import hhplus.ticketing.domain.concert.models.Concert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name="concert")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    String name;


    @Column(name="performer_name")
    String performerName;

    public ConcertEntity(String name, String performerName) {
        this.name = name;
        this.performerName = performerName;
    }

    public static ConcertEntity from(Concert concert) {
        return ConcertEntity.builder()
                .id(concert.getId())
                .name(concert.getName())
                .performerName(concert.getPerformerName())
                .build();
    }

    public static Concert to(ConcertEntity concertEntity) {
        return Concert.builder()
                .id(concertEntity.getId())
                .name(concertEntity.getName())
                .performerName(concertEntity.getPerformerName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcertEntity that = (ConcertEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
