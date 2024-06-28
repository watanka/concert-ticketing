package hhplus.ticketing.api.concert.dto;

import hhplus.ticketing.domain.concert.models.Concert;

import java.util.List;

public record ConcertListResponse(List<Concert> concertList) {

}
