package hhplus.ticketing.ticket.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ticketing.api.ticket.dto.PostTicketRequest;
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
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;

    @Test
    void reserve_ticket() throws Exception {
        LocalDateTime showTime = LocalDateTime.of(2024, 7, 13, 15,0);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                        .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new PostTicketRequest(1, 200000, 1, showTime, 4)
                                    )
                                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets")
            .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
            .contentType(MediaType.APPLICATION_JSON)
            .param("userId", "1"))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].concertName")
                        .value("뉴진스 단독 콘서트"))
                .andExpect(jsonPath("$[1].seatNo")
                        .value("4"))
                .andExpect(jsonPath("$[1].price")
                        .value("200000"))
                .andExpect(jsonPath("$[1].status")
                        .value("PENDING"))
                .andExpect(jsonPath("$[1].concertHall")
                        .value("JAMSIL"));
    }

    @Test
    void view_ticket() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/tickets")
                        .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].concertName")
                        .value("뉴진스 단독 콘서트"))
                .andExpect(jsonPath("$[0].id")
                        .value("1"))
                .andExpect(jsonPath("$[0].seatNo")
                        .value("1"))
                .andExpect(jsonPath("$[0].price")
                        .value("50000"))
                .andExpect(jsonPath("$[0].status")
                        .value("REGISTERED"))
                .andExpect(jsonPath("$[0].reservedTime")
                        .value("2024-05-29T19:32:00"))
                .andExpect(jsonPath("$[0].concertHall")
                        .value("JAMSIL"));
    }

}
