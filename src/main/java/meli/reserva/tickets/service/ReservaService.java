package meli.reserva.tickets.service;

import reactor.core.publisher.Mono;

public interface ReservaService {
	Mono<Boolean> reservaTickets(String showId, String ubicacionNombre, String nroSilla);
}
