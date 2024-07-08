package hhplus.ticketing.api.ticket.dto;


import java.time.LocalDateTime;

public record PostTicketRequest(
        long userId,
        long price,
        long concertId,
        LocalDateTime showTime,
        long seatId
) {


}
