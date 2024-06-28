package hhplus.ticketing.watingqueue.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ticketing.api.watingqueue.dto.TokenRequest;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void register_waiting_queue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tokens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                            new TokenRequest(1))
                        )
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token")
                        .value("TEMPORARY-TOKEN")
                )
                .andDo(print());
    }

    @Test
    void query_waiting_number() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tokens")
                        .header(HttpHeaders.AUTHORIZATION, "TEMPORARY-TOKEN")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId")
                        .value(1))
                .andExpect(jsonPath("$.waitingNo")
                        .value(10))
                .andDo(print());

    }
}

