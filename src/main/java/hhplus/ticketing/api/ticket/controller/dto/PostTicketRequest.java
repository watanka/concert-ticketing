package hhplus.ticketing.api.ticket.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostTicketRequest {

    long userId;
    long showTimeId;
    long seatId;

}
