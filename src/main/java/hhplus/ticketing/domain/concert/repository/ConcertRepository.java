package hhplus.ticketing.domain.concert.repository;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository {
    Concert saveConcert(Concert concert);

    List<Concert> getConcertList();


    ShowTime saveShowTime(ShowTime showTime);

    List<ShowTime> getShowTimeListByConcertId(long concertId);

    public Seat saveSeat(Seat seat);

    List<Seat> getSeatList(long concertId, LocalDateTime showTime);

    Concert findConcertById(long concertId);
}
