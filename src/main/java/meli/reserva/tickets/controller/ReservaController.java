package meli.reserva.tickets.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meli.reserva.tickets.service.ReservaService;
import reactor.core.publisher.Mono;

@RestController
public class ReservaController {

	private final ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@PostMapping("/reservar")
	public Mono<Boolean> reservar(@RequestParam String showId, @RequestParam String ubicacionNombre,
			@RequestParam String nroSilla) {
		return reservaService.reservaTickets(showId, ubicacionNombre, nroSilla);
	}
}
