package hhplus.ticketing.domain.concert.repository;

import com.github.ansell.jdefaultdict.JDefaultDict;
import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.ShowTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcertRepository {
    Map<Long, Concert> concertMap = new HashMap<>();
    Map<Long, List<ShowTime>> showTimeMap = new JDefaultDict<>(k -> new ArrayList<>());



    public Concert saveConcert(Concert concert){
        concertMap.put(concert.getConcertId(), concert);
        return concert;
    }

    public List<Concert> getConcertList(){
        return concertMap.values().stream().toList();
    }

    public Concert findConcertById(long concertId){
        return concertMap.getOrDefault(concertId, null);
    }

    public ShowTime saveShowTime(long concertId, ShowTime showTime) {
        List<ShowTime> showTimeList = showTimeMap.get(concertId);
        showTimeList.add(showTime);
        return showTime;
    }

    public List<ShowTime> getShowTimeListByConcertId(long concertId) {
        return showTimeMap.getOrDefault(concertId, null);
    }
}
