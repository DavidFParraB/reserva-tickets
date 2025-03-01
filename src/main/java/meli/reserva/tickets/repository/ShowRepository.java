package meli.reserva.tickets.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

import meli.reserva.tickets.model.Show;
import reactor.core.publisher.Mono;

public interface ShowRepository extends FirestoreReactiveRepository<Show> {
	Mono<Show> findByShow(String show);
}