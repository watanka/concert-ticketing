package hhplus.ticketing.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        Info info = new Info()
                .version("v0.0.1")
                .title("콘서트 예약 대기열 API")
                .description("대용량 트래픽을 감당하는 콘서트 예약 시스템");

        return new OpenAPI()
                .info(info);

    }

}
