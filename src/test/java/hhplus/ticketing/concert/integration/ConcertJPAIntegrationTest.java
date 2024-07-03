package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.infra.*;
import hhplus.ticketing.domain.concert.infra.entity.ConcertEntity;
import hhplus.ticketing.domain.concert.infra.entity.SeatEntity;
import hhplus.ticketing.domain.concert.infra.entity.ShowTimeEntity;
import hhplus.ticketing.domain.concert.models.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class ConcertJPAIntegrationTest {

    @Autowired
    ConcertJPARepository concertJPARepository;
    @Autowired
    ShowTimeJPARepository showTimeJPARepository;
    @Autowired
    SeatJPARepository seatJPARepository;



    @BeforeEach
    void setUp() {
        LocalDateTime time1 = LocalDateTime.of(2024, 3, 22, 15, 0);
        LocalDateTime time2 = LocalDateTime.of(2024, 3, 23, 15, 0);
        ConcertHall jamsilConcertHall = ConcertHall.JAMSIL;

        ShowTimeEntity showTime1 = new ShowTimeEntity(1, time1, jamsilConcertHall);
        ShowTimeEntity showTime2 = new ShowTimeEntity(1, time2, jamsilConcertHall);
        showTimeJPARepository.save(showTime1);
        showTimeJPARepository.save(showTime2);

    }

    @AfterEach
    void cleanUp(){
        concertJPARepository.deleteAll();
        showTimeJPARepository.deleteAll();
    }

    @Test
    @DisplayName("콘서트 정보를 조회한다.")
    void view_concert() {
        ConcertEntity concert1 = new ConcertEntity( "뉴진스 단독 콘서트", "뉴진스");
        ConcertEntity concert2 = new ConcertEntity( "아이유 10주년 콘서트", "아이유");
        concertJPARepository.save(concert1);
        concertJPARepository.save(concert2);

        ConcertEntity concert = concertJPARepository.findConcertEntityById(1);
        assertThat(concert.getPerformerName()).isEqualTo("뉴진스");
    }

    @Test
    @DisplayName("콘서트 목록을 반환한다.")
    void list_all_registered_concerts() {
        ConcertEntity concert1 = new ConcertEntity( "뉴진스 단독 콘서트", "뉴진스");
        ConcertEntity concert2 = new ConcertEntity( "아이유 10주년 콘서트", "아이유");
        concertJPARepository.save(concert1);
        concertJPARepository.save(concert2);

        List<ConcertEntity> concertEntityList = concertJPARepository.findAll();

        assertThat(concertEntityList.get(0)).isInstanceOf(ConcertEntity.class);
        assertThat(concertEntityList.size()).isEqualTo(2);
    }



    @Test
    @DisplayName("콘서트의 공연시간들을 반환한다.")
    void list_all_showtimes(){

        assertThat(showTimeJPARepository.findShowTimeEntityListByConcertId(1).size())
                .isEqualTo(2);
        assertThat(showTimeJPARepository.findShowTimeEntityListByConcertId(1).get(0).getConcertHall())
                .isEqualTo(ConcertHall.JAMSIL);
    }

    @Test
    @DisplayName("공연시간에 속한 전체 좌석을 반환한다.")
    void list_seats_by_showtime_id(){

        LocalDateTime time = LocalDateTime.of(2024, 3, 3, 17,0);
        for (int i=0; i<5;i++) {
            SeatEntity seat = new SeatEntity(1, time, i, "아이유 10주년 콘서트", ConcertHall.JAMSIL, SeatStatus.AVAILABLE);
            seatJPARepository.save(seat);
        }

        for (int i=5; i<10;i++) {
            SeatEntity seat = new SeatEntity(1, time, i, "아이유 10주년 콘서트", ConcertHall.JAMSIL, SeatStatus.RESERVED);
            seatJPARepository.save(seat);
        }

        List<SeatEntity> seatList = seatJPARepository.getSeatListByConcertIdAndShowTime(1, time);

        assertThat(seatList.size()).isEqualTo(10);
    }
}
