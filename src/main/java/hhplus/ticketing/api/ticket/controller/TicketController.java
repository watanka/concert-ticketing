package hhplus.ticketing.api.ticket.controller;

import hhplus.ticketing.api.ticket.dto.PostTicketRequest;
import hhplus.ticketing.api.ticket.dto.TicketListResponse;
import hhplus.ticketing.api.ticket.dto.TicketResponse;
import hhplus.ticketing.api.ticket.facade.TicketFacade;
import hhplus.ticketing.domain.ticket.components.TicketService;
import hhplus.ticketing.domain.ticket.models.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name="티켓 예약", description="선택한 좌석 예약")
@RequiredArgsConstructor
@RestController
public class TicketController {

    @Autowired
    private final TicketService ticketService;
    @Autowired
    private final TicketFacade ticketFacade;

    @Operation(summary="티켓 발행", description="선택한 좌석과 유저 정보를 사욯하여 티켓을 예약합니다.(예약!=발권) 예약은 5분 후에 만료됩니다.")
    @PostMapping("/tickets")
    @ApiResponse(responseCode = "200", description = "OK")
    void reserveTicket(@RequestHeader(name="Authorization") String token,
                                 @RequestBody PostTicketRequest ticketRequest){

        ticketFacade.register(ticketRequest.userId(), ticketRequest.price(), ticketRequest.concertId(), ticketRequest.showTime(), ticketRequest.seatId());
    }

    @Operation(summary="티켓 조회", description="예약한 티켓(들)을 조회합니다.")
    @GetMapping("/tickets")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array=@ArraySchema(schema=@Schema(implementation = TicketResponse.class))))
    List<TicketResponse> viewTicket(@RequestParam(name="userId") Long userId){

        List<Ticket> ticketList = ticketService.getTicketList(userId);
        return ticketList.stream().map(TicketResponse::fromDomain).toList();
    }
}
