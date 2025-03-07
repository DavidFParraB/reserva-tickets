package meli.reserva.tickets.service;

import meli.reserva.tickets.model.Book;
import reactor.core.publisher.Mono;

public interface ReservaService {
	Mono<Boolean> reservaTickets(Book bookIn);
}
