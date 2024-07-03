package hhplus.ticketing.concert.integration;

import hhplus.ticketing.domain.concert.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JdbcService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void deleteAll() {
        dropConcertTable();
        dropShowTimeTable();
        dropSeatTable();
    }

    public void dropConcertTable() {
        String sql = "DROP TABLE IF EXISTS concert_jdbc";
        jdbcTemplate.execute(sql);
    }

    public void dropShowTimeTable() {
        String sql = "DROP TABLE IF EXISTS showtime_jdbc";
        jdbcTemplate.execute(sql);
    }

    public void dropSeatTable() {
        String sql = "DROP TABLE IF EXISTS seat_jdbc";
        jdbcTemplate.execute(sql);
    }


    public void createConcertTable(){
        String sql = "CREATE TABLE IF NOT EXISTS concert_jdbc (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "performer_name VARCHAR(255) NOT NULL)";
        jdbcTemplate.execute(sql);
    }
    public void createShowTimeTable(){
        String sql = "CREATE TABLE IF NOT EXISTS showtime_jdbc (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "concert_id BIGINT NOT NULL, " +
                "time TIMESTAMP, " +
                "concert_hall VARCHAR(255))";
        jdbcTemplate.execute(sql);
    }

    public void createSeatTable(){
        String sql = "CREATE TABLE IF NOT EXISTS seat_jdbc (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "seat_no BIGINT NOT NULL, " +
                "showtime_id BIGINT NOT NULL, " +
                "concert_hall VARCHAR(255) NOT NULL, " +
                "time TIMESTAMP, " +
                "price BIGINT, " +
                "status BOOLEAN NOT NULL)";
        jdbcTemplate.execute(sql);
    }


    public void saveConcert(String concertName, String performerName){
        String sql = "INSERT INTO concert_jdbc (name, performer_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, concertName, performerName);
    }

    public void saveShowTime(long concertId, LocalDateTime time, ConcertHall concertHall){
        String sql = "INSERT INTO showtime_jdbc (concert_id, time, concert_hall) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, concertId, time, concertHall.toString());
    }

    public void saveSeat(long seatNo, long showTimeId, ConcertHall concertHall, LocalDateTime time, long price, SeatStatus status){
        String sql = "INSERT INTO seat_jdbc (seat_no, showtime_id, concert_hall, time, price, status) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, seatNo, showTimeId, concertHall.toString(), time, price, status.toBool());
    }



    public Concert findConcert(int id) {
        String sql = "SELECT id, name, performer_name FROM concert_jdbc WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Concert concert = Concert.builder()
                    .name(rs.getString("name"))
                    .performerName(rs.getString("performer_name"))
                    .build();
            return concert;
        });

    }

    public List<Concert> getConcertList() {
        String sql = "SELECT * FROM concert_jdbc";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return Concert.builder()
                    .name(rs.getString("name"))
                    .performerName(rs.getString("performer_name"))
                    .build();
        });
    }

    public List<ShowTime> getShowTimeListByConcertId(int id) {
        String sql = "SELECT * FROM showtime_jdbc WHERE concert_id=?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            return ShowTime.builder()
                    .concertId(rs.getInt("concert_id"))
                    .time(rs.getTimestamp("time").toLocalDateTime())
                    .concertHall(ConcertHall.valueOf(rs.getString("concert_hall")))
                    .build();
        });
}

    public List<Seat> getSeatListByShowTimeId(int showtimeId) {
        String sql = "SELECT s.seat_no, s.time, c.name, s.concert_hall, s.price, s.status " +
                "FROM seat_jdbc s " +
                "INNER JOIN showtime_jdbc st ON s.showtime_id=st.id " +
                "INNER JOIN concert_jdbc c ON st.concert_id=c.id " +
                "WHERE showtime_id=?;";

        return jdbcTemplate.query(sql, new Object[]{showtimeId}, (rs, rowNum) -> {
            return Seat.builder()
                    .seatNo(rs.getInt("seat_no"))
                    .showTime(rs.getTimestamp("time").toLocalDateTime())
                    .concertName(rs.getString("name"))
                    .concertHall(ConcertHall.valueOf(rs.getString("concert_hall")))
                    .status(SeatStatus.to(rs.getBoolean("status")))
                    .build();
        });
    }

    public void printConcertTable() {
        String sql = "SELECT * FROM concert_jdbc";

        jdbcTemplate.query(sql, (rs, rowNum) -> {
            System.out.println("Concert ID: " + rs.getLong("id"));
            System.out.println("Concert Name: " + rs.getString("name"));
            System.out.println("Performer Name: " + rs.getString("performer_name"));
            System.out.println("----------------------------------");
            return null;
        });
    }

    public void printShowTimeTable() {
        String sql = "SELECT * FROM showtime_jdbc";

        jdbcTemplate.query(sql, (rs, rowNum) -> {
            System.out.println("Showtime ID: " + rs.getLong("id"));
            System.out.println("Concert ID: " + rs.getLong("concert_id"));
            System.out.println("Time: " + rs.getTimestamp("time"));
            System.out.println("Concert Hall: " + rs.getString("concert_hall"));
            System.out.println("----------------------------------");
            return null;
        });
    }

    public void printSeatTable() {
        String sql = "SELECT * FROM seat_jdbc";

        jdbcTemplate.query(sql, (rs, rowNum) -> {
            System.out.println("Seat ID: " + rs.getLong("id"));
            System.out.println("Seat No: " + rs.getLong("seat_no"));
            System.out.println("Showtime ID: " + rs.getLong("showtime_id"));
            System.out.println("Concert Hall: " + rs.getString("concert_hall"));
            System.out.println("Time: " + rs.getTimestamp("time"));
            System.out.println("Price: " + rs.getLong("price"));
            System.out.println("Status: " + rs.getBoolean("status"));
            System.out.println("----------------------------------");
            return null;
        });
    }


}
