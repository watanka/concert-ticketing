package hhplus.ticketing.domain.ticket.infra;


import hhplus.ticketing.domain.concert.models.ConcertHall;
import hhplus.ticketing.domain.concert.models.Seat;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.models.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="ticket")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="concert_id")
    long concertId;

    @Column(name="concert_hall")
    ConcertHall concertHall;

    @Column(name="showtime")
    LocalDateTime showTime;

    @Column(name="price")
    long price;

    @Column(name="reserved_time")
    LocalDateTime reservedTime;

    @Column(name="seat_no")
    long seatNo;

    @Column(name="user_id")
    long userId;

    @Column(name="status")
    TicketStatus status;


    public static Ticket to(TicketEntity ticketEntity) {
        Seat seat = Seat.builder()
                    .concertId(ticketEntity.getConcertId())
                    .showTime(ticketEntity.getShowTime())
                    .seatNo(ticketEntity.getSeatNo())
                    .concertHall(ticketEntity.getConcertHall())
                    .build();

        return Ticket.builder()
                .id(ticketEntity.getId())
                .seat(seat)
                .userId(ticketEntity.getUserId())
                .status(ticketEntity.getStatus())
                .reservedTime(ticketEntity.getReservedTime())
                .build();
    }

    public static TicketEntity from(Ticket ticket){
        return TicketEntity.builder()
                .id(ticket.getId())
                .concertId(ticket.getConcertId())
                .concertHall(ticket.getConcertHall())
                .showTime(ticket.getShowTime())
                .price(ticket.getPrice())
                .reservedTime(ticket.getReservedTime())
                .seatNo(ticket.getSeatNo())
                .userId(ticket.getUserId())
                .status(ticket.getStatus())
                .build();
    }
}
