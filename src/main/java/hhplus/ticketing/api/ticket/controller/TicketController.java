package hhplus.ticketing.api.ticket.controller;

import hhplus.ticketing.api.point.dto.PointHistoryResponse;
import hhplus.ticketing.api.ticket.dto.PostTicketRequest;
import hhplus.ticketing.api.ticket.dto.TicketListResponse;
import hhplus.ticketing.api.ticket.dto.TicketResponse;
import hhplus.ticketing.api.watingqueue.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name="티켓 예약", description="선택한 좌석 예약")
@RestController
public class TicketController {

    @Operation(summary="티켓 발행", description="선택한 좌석과 유저 정보를 사욯하여 티켓을 예약합니다.(예약!=발권) 예약은 5분 후에 만료됩니다.")
    @PostMapping("/tickets")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TicketResponse.class)))
    TicketResponse reserveTicket(@RequestHeader(name="Authorization") String token,
                                 @RequestBody PostTicketRequest ticketRequest){

        String reservedTime = LocalDateTime.of(2024, 6,27,15,30).toString();
        return new TicketResponse(1, "뉴진스 단독 콘서트", 3, 200000, "PENDING", reservedTime, "JAMSIL");
    }

    @Operation(summary="티켓 조회", description="예약한 티켓(들)을 조회합니다.")
    @GetMapping("/tickets")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = TicketListResponse.class))))
    TicketListResponse viewTicket(@RequestParam(name="userId") Long userId){
        String reservedTime = LocalDateTime.of(2024, 6,27,15,30).toString();
        return new TicketListResponse(
                List.of(new TicketResponse(1, "뉴진스 단독 콘서트", 3, 200000, "PENDING", reservedTime, "JAMSIL"))
        );
    }
}
