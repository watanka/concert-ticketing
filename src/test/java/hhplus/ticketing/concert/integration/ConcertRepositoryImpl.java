package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {


    private final ConcertJPARepository concertJPARepository;

    @Autowired
    public ConcertRepositoryImpl(ConcertJPARepository concertJPARepository) {
        this.concertJPARepository = concertJPARepository;
    }


    @Override
    public Concert saveConcert(Concert concert) {
        return null;
    }

    @Override
    public List<Concert> getConcertList() {
        return null;
    }

    @Override
    public ShowTime saveShowTime(long concertId, ShowTime showTime) {
        return null;
    }

    @Override
    public List<ShowTime> getShowTimeListByConcertId(long concertId) {
        return null;
    }

    @Override
    public Seat saveSeat(long showTimeId, Seat seat) {
        return null;
    }

    @Override
    public List<Seat> getAvailableSeatList(long concertId) {
        return null;
    }

    @Override
    public Concert findConcertByName(String concertName) {
        return null;
    }
}
