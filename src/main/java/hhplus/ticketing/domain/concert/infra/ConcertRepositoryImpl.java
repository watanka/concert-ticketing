package hhplus.ticketing.domain.concert.infra;

import hhplus.ticketing.domain.concert.infra.entity.ConcertEntity;
import hhplus.ticketing.domain.concert.infra.entity.SeatEntity;
import hhplus.ticketing.domain.concert.infra.entity.ShowTimeEntity;
import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public ShowTime saveShowTime(ShowTime showTime) {
        ShowTimeEntity showTimeEntity = showTimeJPARepository.save(ShowTimeEntity.from(showTime));
        return ShowTimeEntity.to(showTimeEntity);
    }

    @Override
    public List<ShowTime> getShowTimeListByConcertId(long concertId) {
        List<ShowTimeEntity> showTimeEntityList = showTimeJPARepository.findShowTimeEntityListByConcertId(concertId);

        return showTimeEntityList.stream()
                .map(ShowTimeEntity::to)
                .toList();
    }

    @Override
    public Seat saveSeat(Seat seat) {
        SeatEntity seatEntity = seatJPARepository.save(SeatEntity.from(seat));
        return SeatEntity.to(seatEntity);
    }

    @Override
    public List<Seat> getSeatList(long concertId, LocalDateTime showTime) {
        List<SeatEntity> seatEntityList = seatJPARepository.getSeatListByConcertIdAndShowTime(concertId, showTime);
        return seatEntityList.stream()
                .map(SeatEntity::to)
                .toList();
    }

    @Override
    public Concert findConcertById(long concertId) {
        ConcertEntity concertEntity = concertJPARepository.findConcertEntityById(concertId);
        return ConcertEntity.to(concertEntity);
    }

    @Override
    public Seat getSeat(long concertId, LocalDateTime showTime, long seatId) {
        return SeatEntity.to(seatJPARepository.getSeatByConcertIdAndShowTimeAndSeatNo(concertId, showTime, seatId));
    }

    public void deleteAll(){
        concertJPARepository.deleteAll();
    }
}
