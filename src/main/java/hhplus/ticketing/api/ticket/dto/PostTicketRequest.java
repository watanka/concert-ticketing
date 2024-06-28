package hhplus.ticketing.api.ticket.dto;


public record PostTicketRequest(long userId,
        long showTimeId,
        long seatId
) {


}
