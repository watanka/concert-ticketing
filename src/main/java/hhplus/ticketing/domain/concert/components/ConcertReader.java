package hhplus.ticketing.domain.concert.components;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.infra.MemoryConcertRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class ConcertReader {

    private final MemoryConcertRepository repository;

    public List<Concert> getConcertList(){
        return repository.getConcertList();
    }

    public Concert registerConcert(Concert concert){
        return repository.saveConcert(concert);
    }

    public Concert getConcert(long concertId) {
        return repository.findConcertById(concertId);
    }

    public ShowTime registerShowTime(long concertId, ShowTime showTime){
        return repository.saveShowTime(concertId, showTime);
    }

    public List<ShowTime> getShowTimeList(long concertId) {
        return repository.getShowTimeListByConcertId(concertId);

    }
}
