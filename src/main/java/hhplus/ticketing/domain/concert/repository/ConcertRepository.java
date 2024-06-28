package hhplus.ticketing.domain.concert.repository;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository {
    Concert saveConcert(Concert concert);

    List<Concert> getConcertList();


    ShowTime saveShowTime(long concertId, ShowTime showTime);

    List<ShowTime> getShowTimeListByConcertId(long concertId);

    Seat saveSeat(long showTimeId, Seat seat);

    List<Seat> getAvailableSeatList(long concertId);

    Concert findConcertByName(String concertName);
}
