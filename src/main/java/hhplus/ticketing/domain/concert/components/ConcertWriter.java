package hhplus.ticketing.domain.concert.components;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepositoryInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConcertWriter {
    private final ConcertRepositoryInterface repository;

    public Concert registerConcert(Concert concert){
        return repository.saveConcert(concert);
    }

    public ShowTime registerShowTime(long concertId, ShowTime showTime){
        return repository.saveShowTime(concertId, showTime);
    }

    public Seat registerSeat(long showTimeId, Seat seat) {
        return repository.saveSeat(showTimeId, seat);
    }

}
