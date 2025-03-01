package meli.reserva.tickets.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.service.ShowService;
import reactor.core.publisher.Flux;

@RestController
public class ShowController {

	private final ShowService showService;

	public ShowController(ShowService showService) {
		this.showService = showService;
	}

	@GetMapping("/shows")
	public Flux<Show> getAllShows() {
		return showService.getAllShows();
	}
}
