package hhplus.ticketing.domain.concert.infra;

import com.github.ansell.jdefaultdict.JDefaultDict;
import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepositoryInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryConcertRepository implements ConcertRepositoryInterface {
    Map<Long, Concert> concertMap = new HashMap<>();
    Map<Long, List<ShowTime>> showTimeMap = new JDefaultDict<>(k -> new ArrayList<>());

    Map<Long, List<Seat>> seatMap = new JDefaultDict<>(k -> new ArrayList<>());


    @Override
    public Concert saveConcert(Concert concert){
        concertMap.put(concert.getConcertId(), concert);
        return concert;
    }

    @Override
    public List<Concert> getConcertList(){
        return concertMap.values().stream().toList();
    }

    @Override
    public Concert findConcertById(long concertId){
        return concertMap.getOrDefault(concertId, null);
    }

    @Override
    public ShowTime saveShowTime(long concertId, ShowTime showTime) {
        List<ShowTime> showTimeList = showTimeMap.get(concertId);
        showTimeList.add(showTime);
        return showTime;
    }

    @Override
    public List<ShowTime> getShowTimeListByConcertId(long concertId) {
        return showTimeMap.getOrDefault(concertId, null);
    }

    @Override
    public Seat saveSeat(long showTimeId, Seat seat) {
        List<Seat> seatList = seatMap.get(showTimeId);
        seatList.add(seat);
        return seat;
    }

    @Override
    public List<Seat> getAvailableSeatList(long showTimeId) {
        List<Seat> seatList = seatMap.get(showTimeId);
        return seatList.stream()
                .filter(s -> s.getStatus() == SeatStatus.AVAILABLE)
                .toList();
    }


}
