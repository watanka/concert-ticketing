package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.models.ConcertHall;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void view_concert_list() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/concerts")
                        .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.concertList[0].concertName")
                                .value("뉴진스 단독 콘서트"))
                        .andExpect(jsonPath("$.concertList[0].performerName")
                            .value("뉴진스"));
    }


    @Test
    void view_showtime_list() throws Exception{
        long concertId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/concerts/"+concertId)
                        .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.showTimeList[0].time")
                        .value("2024-06-26T15:00"))
                .andExpect(jsonPath("$.showTimeList[0].concertHall")
                        .value("JAMSIL"));
    }

    @Test
    void view_seat_list_of_the_showtime() throws Exception{
        long concertId = 1;
        long showTimeId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/concerts/"+concertId+"/"+showTimeId)
                        .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seatList[0].seatNo")
                        .value("1"))
                .andExpect(jsonPath("$.seatList[0].status")
                        .value("AVAILABLE"))
                .andExpect(jsonPath("$.seatList[0].price")
                        .value("300000"))
        ;
    }
}