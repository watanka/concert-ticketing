package hhplus.ticketing.api.ticket.controller;

import hhplus.ticketing.api.ticket.dto.PostTicketRequest;
import hhplus.ticketing.api.ticket.dto.TicketListResponse;
import hhplus.ticketing.api.ticket.dto.TicketResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TicketController {

    @PostMapping("/tickets")
    TicketResponse reserveTicket(@RequestHeader(name="Authorization") String token,
                                 @RequestBody PostTicketRequest ticketRequest){

        String reservedTime = LocalDateTime.of(2024, 6,27,15,30).toString();
        return new TicketResponse(1, "뉴진스 단독 콘서트", 3, 200000, "PENDING", reservedTime, "JAMSIL");
    }

    @GetMapping("/tickets")
    TicketListResponse viewTicket(@RequestParam(name="userId") Long userId){
        String reservedTime = LocalDateTime.of(2024, 6,27,15,30).toString();
        return new TicketListResponse(
                List.of(new TicketResponse(1, "뉴진스 단독 콘서트", 3, 200000, "PENDING", reservedTime, "JAMSIL"))
        );
    }
}
