package hhplus.ticketing.concert.unit;

import hhplus.ticketing.domain.concert.components.ConcertReader;
import hhplus.ticketing.domain.concert.components.ConcertWriter;
import hhplus.ticketing.domain.concert.models.*;
import hhplus.ticketing.domain.concert.infra.MemoryConcertRepository;
import hhplus.ticketing.domain.concert.repository.ConcertRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


public class ConcertReaderTest {

    private ConcertRepository repository = new MemoryConcertRepository();
    private ConcertReader concertReader = new ConcertReader(repository);
    private ConcertWriter concertWriter = new ConcertWriter(repository);

    @BeforeEach
    void setUp(){
        long concertId = 1;

        Concert concert = new Concert("뉴진스 단독 콘서트", "뉴진스");
        concertWriter.registerConcert(concert);


        long showTimeId = 1;
        LocalDateTime time = LocalDateTime.of(2024, 3, 22, 15, 0);
        ConcertHall jamsilConcertHall = ConcertHall.JAMSIL;

        ShowTime showTime = new ShowTime(concertId, time, jamsilConcertHall);
        concertWriter.registerShowTime(showTime);

    }

    @Test
    @DisplayName("콘서트 정보를 조회한다.")
    void register_concert(){
        Assertions.assertThat(concertReader.findConcert(0).getPerformerName())
                .isEqualTo("뉴진스");

    }

    @Test
    @DisplayName("콘서트 목록을 반환한다.")
    void list_all_registered_concerts(){
        Assertions.assertThat(concertReader.getConcertList().get(0).getId())
                .isEqualTo(0);
    }


    @Test
    @DisplayName("콘서트의 공연시간들을 반환한다.")
    void list_all_showtimes(){
        Assertions.assertThat(concertReader.getShowTimeList(1).get(0).getConcertHall())
                .isEqualTo(ConcertHall.JAMSIL);
    }

    @Test
    @DisplayName("예매가능한 좌석을 반환한다.")
    void list_available_seats(){
        LocalDateTime showTime = LocalDateTime.now();
        for (int i=0; i<10;i++) {
            Seat seat = Seat.builder()
                    .seatNo(1)
                    .concertName("아이유 10주년 콘서트")
                    .concertHall(ConcertHall.JAMSIL)
                    .showTime(showTime)
                    .price(100000)
                    .status(SeatStatus.AVAILABLE)
                    .build();
            if (i>=5){
                seat.updateStatus(SeatStatus.RESERVED);
            }
            concertWriter.registerSeat(seat);
        }

        List<Seat> seatList = concertReader.getAvailableSeats(1, showTime );
        Assertions.assertThat(seatList.size()).isEqualTo(5);
        for (Seat seat : seatList) {
            Assertions.assertThat(seat.getStatus()).isEqualTo(SeatStatus.AVAILABLE);
        }

    }


}

