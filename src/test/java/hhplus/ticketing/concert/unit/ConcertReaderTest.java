package hhplus.ticketing.concert.unit;

import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.models.Concert;
import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.ShowTime;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class ConcertReaderTest {

    private ConcertRepository repository = new ConcertRepository();
    private ConcertReader concertReader = new ConcertReader(repository);

    @BeforeEach
    void setUp(){
        long concertId = 1;

        Concert concert = new Concert(concertId, "뉴진스 단독 콘서트", "뉴진스");
        concertReader.registerConcert(concert);


        long showTimeId = 1;
        LocalDateTime time = LocalDateTime.of(2024, 3, 22, 15, 0);
        ConcertHall jamsilConcertHall = ConcertHall.JAMSIL;

        ShowTime showTime = new ShowTime(showTimeId, concertId, time, jamsilConcertHall);
        concertReader.registerShowTime(concertId, showTime);

    }

    @Test
    @DisplayName("콘서트 정보를 조회한다.")
    void register_concert(){
        Assertions.assertThat(concertReader.getConcert(1).getConcertName())
                .isEqualTo("뉴진스 단독 콘서트");

    }

    @Test
    @DisplayName("콘서트 목록을 반환한다.")
    void list_all_registered_concerts(){
        Assertions.assertThat(concertReader.getConcertList().get(0).getConcertName())
                .isEqualTo("뉴진스 단독 콘서트");
    }


    @Test
    @DisplayName("콘서트의 공연시간들을 반환한다.")
    void list_all_showtimes(){
        Assertions.assertThat(concertReader.getShowTimeList(1).get(0).getConcertHall())
                .isEqualTo(ConcertHall.JAMSIL);
    }


}

