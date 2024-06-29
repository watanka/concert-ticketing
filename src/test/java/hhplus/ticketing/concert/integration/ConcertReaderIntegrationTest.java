package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.components.ConcertWriter;
import hhplus.ticketing.domain.concert.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

//        long showTimeId = 1;
//        LocalDateTime time = LocalDateTime.of(2024, 3, 22, 15, 0);
//        ConcertHall jamsilConcertHall = ConcertHall.JAMSIL;
//
//        ShowTime showTime = new ShowTime(concertId, time, jamsilConcertHall);
//        concertWriter.registerShowTime(concertId, showTime);

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
    @DisplayName("예매가능한 좌석을 반환한다.")
    void list_available_seats(){
        for (int i=0; i<10;i++) {
            Seat seat = new Seat(i,
                    "아이유 10주년 콘서트",
                    ConcertHall.JAMSIL,
                    LocalDateTime.of(2024, 3, 3, 17,0),
                    100000,
                    SeatStatus.AVAILABLE);
            if (i>=5){
                seat.updateStatus(SeatStatus.RESERVED);
            }
            concertWriter.registerSeat(1, seat);
        }

        List<Seat> seatList = concertReader.getAvailableSeats(1);
        assertThat(seatList.size()).isEqualTo(5);
        for (Seat seat : seatList) {
            assertThat(seat.getStatus()).isEqualTo(SeatStatus.AVAILABLE);
        }

    }

}
