package hhplus.ticketing.point.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ticketing.api.point.dto.PointRequest;
import hhplus.ticketing.domain.point.models.PointType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
                                new PointRequest(1, 100000, PointType.RECHARGE.toString()))

                        )
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("잔액이 부족할 경우 포인트 사용에 실패한다.")
    void recharge_point_fail() throws Exception {
        // userId=1의 유저의 잔액은 10만원 이하이다.
        mockMvc.perform(MockMvcRequestBuilders.post("/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new PointRequest(1, 10000000, PointType.USE.toString()))

                        )
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    void query_point() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/point_history")
                        .param("userId", String.valueOf(1))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].amount")
                        .value(50000))
                .andExpect(jsonPath("$[0].type")
                        .value(PointType.RECHARGE.toString()))
                .andDo(print());

    }


}
