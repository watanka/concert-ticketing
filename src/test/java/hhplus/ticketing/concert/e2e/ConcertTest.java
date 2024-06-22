package hhplus.ticketing.concert.e2e;

import hhplus.ticketing.api.concert.controller.ConcertController;
import hhplus.ticketing.api.concert.dto.ConcertResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;


import java.net.URI;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ConcertTest {
    @Autowired
    private ConcertController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("/concerts - 콘서트 전체리스트를 리턴함")
    void return_all_concert_list(){
        //TODO: token
        RequestEntity<Void> request = RequestEntity.get(URI.create("/concerts")).build();
        ResponseEntity<ConcertResponse> response = restTemplate.exchange(request, ConcertResponse.class);

        assertThat(response.getBody().concertList()).isInstanceOf(ArrayList.class);
    }
}
