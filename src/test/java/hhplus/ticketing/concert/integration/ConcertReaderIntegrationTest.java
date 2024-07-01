package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.components.ConcertWriter;
import hhplus.ticketing.domain.concert.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertReaderIntegrationTest {
    @Autowired
    ConcertReader concertReader;
    @Autowired
    ConcertWriter concertWriter;

    @BeforeEach
    void setUp(){

        long showTimeId = 1;
        long concertId = 1;
        LocalDateTime time = LocalDateTime.of(2024, 3, 22, 15, 0);
        ConcertHall jamsilConcertHall = ConcertHall.JAMSIL;


        ShowTime showTime = new ShowTime(concertId, time, jamsilConcertHall);
        concertWriter.registerShowTime(showTime);
//
    }

    @Test
    @DisplayName("콘서트 정보를 조회한다.")
    void register_concert(){
        Concert concert1 = new Concert("뉴진스 단독 콘서트", "뉴진스");
        Concert concertRegistered1 = concertWriter.registerConcert(concert1);

        Concert concert2 = new Concert("아이유 10주년 콘서트", "아이유");
        Concert concertRegistered2 = concertWriter.registerConcert(concert2);


        for (Concert c : concertReader.getConcertList()) {
            System.out.println(c.getName() + " " + c.getId());
        }
        System.out.println(concertRegistered1.getId());
        assertThat(concertReader.findConcert(concertRegistered1.getId()).getPerformerName())
                .isEqualTo("뉴진스");

    }

    @Test
    @DisplayName("콘서트 목록을 반환한다.")
    void list_all_registered_concerts(){
        assertThat(concertReader.getConcertList().get(0).getName())
                .isEqualTo("뉴진스 단독 콘서트");
    }


    @Test
    @DisplayName("콘서트의 공연시간들을 반환한다.")
    void list_all_showtimes(){
        assertThat(concertReader.getShowTimeList(1).get(0).getConcertHall())
                .isEqualTo(ConcertHall.JAMSIL);
    }

    @Test
    @DisplayName("공연시간의 좌석들을 반환한다.")
    void list_available_seats(){
        LocalDateTime showTime = LocalDateTime.of(2024, 3, 3, 17,0);
        for (int i=0; i<10;i++) {
            Seat seat = new Seat(i,
                                2, "아이유 10주년 콘서트",
                                 ConcertHall.JAMSIL,
                                 showTime,
                                100000,
                                 SeatStatus.AVAILABLE);
            concertWriter.registerSeat(seat);
            }

        List<Seat> seatList = concertReader.getSeatList(2, showTime);
        assertThat(seatList.size()).isEqualTo(10);


    }

}
