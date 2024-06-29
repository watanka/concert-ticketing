package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.models.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JdbcService.class)
public class ConcertJdbcTest {

    @Autowired
    JdbcService jdbcService;


    @BeforeEach
    void setUp() {
        long concertId = 1;
        jdbcService.createConcertTable();
        jdbcService.createShowTimeTable();
        jdbcService.createSeatTable();
        jdbcService.saveConcert("뉴진스 단독 콘서트", "뉴진스");
        jdbcService.saveConcert("아이유 10주년 콘서트", "아이유");
        jdbcService.printConcertTable();
        LocalDateTime time1 = LocalDateTime.of(2024, 3, 22, 15, 0);
        LocalDateTime time2 = LocalDateTime.of(2024, 3, 23, 15, 0);
        ConcertHall jamsilConcertHall = ConcertHall.JAMSIL;

        jdbcService.saveShowTime(concertId, time1, jamsilConcertHall);
        jdbcService.saveShowTime(concertId, time2, jamsilConcertHall);

    }

    @AfterEach
    void cleanUp(){
        jdbcService.deleteAll();
    }

    @Test
    @DisplayName("콘서트 정보를 조회한다.")
    void register_concert() {
        Concert concert = jdbcService.findConcert(1);

        assertThat(concert.getPerformerName()).isEqualTo("뉴진스");
    }

    @Test
    @DisplayName("콘서트 목록을 반환한다.")
    void list_all_registered_concerts() {
        assertThat(jdbcService.getConcertList().get(0)).isInstanceOf(Concert.class);
        assertThat(jdbcService.getConcertList().size()).isEqualTo(2);
    }



    @Test
    @DisplayName("콘서트의 공연시간들을 반환한다.")
    void list_all_showtimes(){
        Assertions.assertThat(jdbcService.getShowTimeListByConcertId(1).size())
                .isEqualTo(2);
        Assertions.assertThat(jdbcService.getShowTimeListByConcertId(1).get(0).getConcertHall())
                .isEqualTo(ConcertHall.JAMSIL);
    }

    @Test
    @DisplayName("공연시간에 속한 전체 좌석을 반환한다.")
    void list_seats_by_showtime_id(){

        LocalDateTime time = LocalDateTime.of(2024, 3, 3, 17,0);
        for (int i=0; i<5;i++) {
            jdbcService.saveSeat(i, 1, ConcertHall.JAMSIL, time, 200000, SeatStatus.AVAILABLE);
        }

        for (int i=5; i<10;i++) {
            jdbcService.saveSeat(i, 1, ConcertHall.JAMSIL, time, 200000, SeatStatus.RESERVED);
        }

        List<Seat> seatList = jdbcService.getSeatListByShowTimeId(1);

        assertThat(seatList.size()).isEqualTo(10);
    }

}
