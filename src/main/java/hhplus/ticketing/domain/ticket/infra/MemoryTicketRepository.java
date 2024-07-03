package hhplus.ticketing.domain.ticket.infra;

import com.github.ansell.jdefaultdict.JDefaultDict;
import hhplus.ticketing.domain.ticket.models.Ticket;
import hhplus.ticketing.domain.ticket.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryTicketRepository implements TicketRepository {

    Map<Long, Ticket> ticketByUserIdMap = new HashMap<>();
    Map<Long, Ticket> ticketByIdMap = new HashMap<>();
    Map<String, List<Ticket>> ticketsByConcertIdAndShowTimeMap = new JDefaultDict<>(k -> new ArrayList<>());

    @Override
    public Ticket findByUserId(long userId) {
        return ticketByUserIdMap.get(userId);
    }

    @Override
    public List<Ticket> findByConcertIdAndShowTime(long concertId, LocalDateTime showTime) {
        return ticketsByConcertIdAndShowTimeMap.get(createShowTimeKey(concertId, showTime));
    }

    @Override
    public boolean existsByConcertIdAndShowTimeAndSeatNo(long concertId, LocalDateTime showTime, long seatNo) {
        List<Ticket> ticketList = ticketsByConcertIdAndShowTimeMap.get(createShowTimeKey(concertId, showTime));
        System.out.println(ticketsByConcertIdAndShowTimeMap);
        return ticketList.stream().anyMatch(ticket -> ticket.getSeatNo() == seatNo);
    }

    @Override
    public Ticket findById(long ticketId) {
        return ticketByIdMap.get(ticketId);
    }

    @Override
    public Ticket save(Ticket ticket) {
        ticketByUserIdMap.put(ticket.getUserId(), ticket);
        ticketByIdMap.put(ticket.getId(), ticket);
        ticketsByConcertIdAndShowTimeMap.get(createShowTimeKey(ticket.getConcertId(), ticket.getShowTime())).add(ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllPendingTicket() {
        return null;
    }

    private String createShowTimeKey(long concertId, LocalDateTime showTime) {
        return concertId + "_" + showTime.toString();
    }


}
