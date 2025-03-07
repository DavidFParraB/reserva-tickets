package meli.reserva.tickets.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import meli.reserva.tickets.model.Book;
import meli.reserva.tickets.service.ReservaService;
import reactor.core.publisher.Mono;

@RestController
public class ReservaController {

	private final ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@PostMapping("/reservar")
	public Mono<Boolean> reservar(@RequestBody Book bookIn) {
		return reservaService.reservaTickets(bookIn);
	}
}
