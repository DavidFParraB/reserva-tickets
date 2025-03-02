package meli.reserva.tickets.service;

import meli.reserva.tickets.model.Show;
import reactor.core.publisher.Flux;

public interface ShowService {
	Flux<Show> getAllShows();

	void saveShow(Show show);
}
