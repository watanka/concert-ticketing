package hhplus.ticketing.domain.concert.components;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertWriter {
    @Autowired
    private final ConcertRepository repository;

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
