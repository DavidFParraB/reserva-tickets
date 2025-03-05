package meli.reserva.tickets.service;

import meli.reserva.tickets.model.Show;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShowService {
	Flux<Show> getAllShows();

	Mono<Show> findShowById(String show);

	void saveShow(Show show);
}
