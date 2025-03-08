package meli.reserva.tickets.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import meli.reserva.tickets.model.Seat;

@Repository
public interface SeatRepository {
	void saveSeats(List<Seat> seats, String showId, String locationId);

	List<Seat> seatsByLocation(String showId, String locationId);
}