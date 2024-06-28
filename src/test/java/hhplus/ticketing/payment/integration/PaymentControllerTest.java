package hhplus.ticketing.payment.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ticketing.api.payment.dto.PaymentRequest;
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
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void transact_payment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new PaymentRequest(1, 3, 200000))
                        )
                )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void view_user_payment_history() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payment_history")
                        .param("userId", String.valueOf(1))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.paymentHistory[0].ticketId")
                        .value(1))
                .andExpect(jsonPath("$.paymentHistory[0].transactionTime")
                        .value("2024-06-27T00:30"))
                .andExpect(jsonPath("$.paymentHistory[0].orderTotal")
                        .value(200000))
                .andDo(print());

    }
}
