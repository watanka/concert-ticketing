package hhplus.ticketing.domain.concert.components;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertRepository repository;

    public List<Concert> getConcertList(){
        return repository.getConcertList();
    }



    public Concert findConcert(long concertId) {
        return repository.findConcertById(concertId);
    }

    public ShowTime findShowTime(long concertId, LocalDateTime showTime){
        return repository.findShowTimeByConcertIdAndTime(concertId, showTime);
    }

    public List<ShowTime> getShowTimeList(long concertId) {
        return repository.getShowTimeListByConcertId(concertId);

    }


    public List<Seat> getSeatList(long concertId, LocalDateTime showTime) {
        return repository.getSeatList(concertId, showTime);
    }

}
