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

    public Seat registerSeat(long showTimeId, Seat seat) {
        return repository.saveSeat(showTimeId, seat);
    }

    public List<Seat> getAvailableSeats(long concertId) {
        return repository.getAvailableSeatList(concertId);
    }

}
