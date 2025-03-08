package meli.reserva.tickets.service;

import java.util.List;

import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Show;

public interface ShowService {
	Boolean saveShow(Show show);

	List<Show> getShowsByFilter(Filter filter);
	
	Show getShowById(String showId);
}
