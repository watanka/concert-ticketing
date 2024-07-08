package hhplus.ticketing.api.ticket.dto;

import hhplus.ticketing.domain.ticket.models.Ticket;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TicketResponse(long id,
                             String concertName,

                             LocalDateTime showTime,
                             long seatNo,
                             long price,
                             String status,
                             LocalDateTime reservedTime,
                             String concertHall) {


    public static TicketResponse fromDomain(Ticket ticket){
        return TicketResponse.builder()
                .id(ticket.getId())
                .concertName(ticket.getConcertName())
                .showTime(ticket.getShowTime())
                .seatNo(ticket.getSeatNo())
                .price(ticket.getPrice())
                .status(ticket.getStatus().toString())
                .reservedTime(ticket.getReservedTime())
                .concertHall(ticket.getConcertHall().toString())
                .build();

    }

}
