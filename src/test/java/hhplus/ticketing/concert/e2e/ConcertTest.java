package hhplus.ticketing.concert.e2e;

import hhplus.ticketing.api.concert.controller.ConcertController;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


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
    @Disabled
    void return_all_concert_list(){
        //TODO: token

        String path = "http://localhost/concerts";
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get(path)
                .then().log().all().extract();

        System.out.println(response.body());
        assertThat(response.body()).isInstanceOf(ArrayList.class);
    }
}
