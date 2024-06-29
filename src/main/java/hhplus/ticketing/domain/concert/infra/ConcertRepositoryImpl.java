package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.ConcertEntity;
import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    @Autowired
    private final ConcertJPARepository concertJPARepository;
    @Autowired
    private final ShowTimeJPARepository showTimeJPARepository;
    @Autowired
    private final SeatJPARepository seatJPARepository;

    @Override
    public Concert saveConcert(Concert concert) {
        ConcertEntity concertEntity = ConcertEntity.from(concert);
        return ConcertEntity.to(concertJPARepository.save(concertEntity));
    }

    @Override
    public List<Concert> getConcertList() {
        List<ConcertEntity> concertEntityList = concertJPARepository.findAll();
        return concertEntityList.stream()
                .map(ConcertEntity::to)
                .toList();
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
    public Concert findConcertById(long concertId) {
        ConcertEntity concertEntity = concertJPARepository.findConcertEntityById(concertId);
        return ConcertEntity.to(concertEntity);
    }

    public void deleteAll(){
        concertJPARepository.deleteAll();
    }
}
