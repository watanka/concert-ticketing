package hhplus.ticketing.api.ticket.dto;

public record TicketResponse(long id,
        String concertName,
        long seatNo,
        long price,
        String ticketStatus,
        String reservedTime,
        String concertHall) {


}
