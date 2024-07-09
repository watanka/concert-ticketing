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

    @Test
    @DisplayName("유효하지 않은 토큰을 조회할 경우, 에러가 발생한다.")
    void query_waiting_num_with_INVALID_token() throws Exception {
        long concertId = 2;
        long userId = 1;


        mockMvc.perform(MockMvcRequestBuilders.get("/tokens")
                        .header(HttpHeaders.AUTHORIZATION, "INVALID-TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(concertId))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Invalid Token"));


    }



    private Token requestToken(long concertId, long userId) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/tokens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new TokenRequest(concertId, userId))
                        )
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Token token = objectMapper.readValue(jsonResponse, Token.class);
        return token;
    }


    private boolean isValidToken(Token token){
        return jwtTokenManager.validate(token) &&
                token != null &&
                token.getJwt().split("\\.").length == 3;
    }
}

