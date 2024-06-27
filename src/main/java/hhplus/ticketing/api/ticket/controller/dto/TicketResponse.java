package hhplus.ticketing.api.ticket.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketResponse {

    long id;
    String concertName;
    long seatNo;
    long price;
    String ticketStatus;
    String reservedTime;
    String concertHall;
}
