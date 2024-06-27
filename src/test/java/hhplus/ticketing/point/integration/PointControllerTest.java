package hhplus.ticketing.point.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ticketing.api.point.controller.dto.PointRequest;
import hhplus.ticketing.domain.point.models.PointType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void recharge_point() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new PointRequest(2, 100000, PointType.RECHARGE.toString()))

                        )
                )
                .andExpect(status().isOk())
                .andDo(print())
        ;

    }

    @Test
    void query_point() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/point_history")
                        .param("userId", String.valueOf(1))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pointHistory[0].type")
                        .value(PointType.RECHARGE.toString()))
                .andExpect(jsonPath("$.balance")
                        .value(20000))
                .andDo(print());

    }


}
