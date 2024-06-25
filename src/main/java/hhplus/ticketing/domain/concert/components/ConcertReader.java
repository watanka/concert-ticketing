package hhplus.ticketing.domain.concert.components;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.infra.MemoryConcertRepository;
import hhplus.ticketing.domain.concert.repository.ConcertRepositoryInterface;
import lombok.RequiredArgsConstructor;


import java.util.List;


@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertRepositoryInterface repository;

    public List<Concert> getConcertList(){
        return repository.getConcertList();
    }



    public Concert getConcert(String concertName) {
        return repository.findConcertByName(concertName);
    }



    public List<ShowTime> getShowTimeList(long concertId) {
        return repository.getShowTimeListByConcertId(concertId);

    }


    public List<Seat> getAvailableSeats(long concertId) {
        return repository.getAvailableSeatList(concertId);
    }

}
