package hhplus.ticketing.domain.concert.infra;

import com.github.ansell.jdefaultdict.JDefaultDict;
import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.concert.models.SeatStatus;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryConcertRepository implements ConcertRepository {
    Map<Long, Concert> concertMap = new HashMap<>();
    Map<Long, List<ShowTime>> showTimeMap = new JDefaultDict<>(k -> new ArrayList<>());

    Map<String, List<Seat>> seatMap = new JDefaultDict<>(k -> new ArrayList<>());


    @Override
    public Concert saveConcert(Concert concert){
        concertMap.put(concert.getId(), concert);
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
    public ShowTime saveShowTime(ShowTime showTime) {
        List<ShowTime> showTimeList = showTimeMap.get(showTime.getConcertId());
        showTimeList.add(showTime);
        return showTime;
    }

    @Override
    public List<ShowTime> getShowTimeListByConcertId(long concertId) {
        return showTimeMap.getOrDefault(concertId, null);
    }

    @Override
    public Seat saveSeat(Seat seat) {
        String showTimeKey = setShowTimeKey(seat.getConcertId(), seat.getShowTime());
        List<Seat> seatList = seatMap.get(showTimeKey);
        seatList.add(seat);
        return seat;
    }

    @Override
    public List<Seat> getSeatList(long concertId, LocalDateTime showTime) {
        String showTimeId = setShowTimeKey(concertId, showTime);

        return seatMap.get(showTimeId);
    }


    private String setShowTimeKey(long concertId, LocalDateTime time){
        return String.valueOf(concertId) + "_" + time.toString();
    }

}
