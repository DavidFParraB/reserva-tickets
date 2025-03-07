package meli.reserva.tickets.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.service.ShowService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@GetMapping("/shows/{showId}")
	public Mono<Show> getShowById(@PathVariable String showId) {
		return showService.findShowById(showId);
	}

	@GetMapping("/shows/{fechaInicio}/{fechaFin}")
	public Flux<Show> getShowsByFilter(@PathVariable String fechaInicio, @PathVariable String fechaFin) {
		return showService.findShowsByFecha(fechaInicio, fechaFin);
	}

	@PostMapping(("/shows"))
	public String fillShows(@RequestBody Show show) {
		showService.saveShow(show);
		return "OK";
	}
}
