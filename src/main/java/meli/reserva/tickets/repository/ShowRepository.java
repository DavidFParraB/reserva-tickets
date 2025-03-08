package meli.reserva.tickets.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Show;

@Repository
public interface ShowRepository {
	List<Show> findShowsByFilters(Filter filter);

	Show findById(String showId);

	void saveShow(Show show);
}