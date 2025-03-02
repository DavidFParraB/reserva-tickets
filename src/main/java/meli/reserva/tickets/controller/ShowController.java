package meli.reserva.tickets.controller;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.model.Ubicacion;
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

	@GetMapping("/shows/fill")
	public String fillShows() {
		Show show = new Show();
		show.setShow("show_uno");
		show.setFechaPresentacion("2025-01-21");

		Ubicacion platea = new Ubicacion();
		platea.setNombre("platea");
		platea.setPrecio(1000);
		platea.setCapacidad(100);
		platea.setDisponibles(100);

		Ubicacion platino = new Ubicacion();
		platino.setNombre("platino");
		platino.setPrecio(2000);
		platino.setCapacidad(50);
		platino.setDisponibles(35);

		show.setUbicacion(Arrays.asList(platea, platino));
		showService.saveShow(show);
		return "OK";
	}
}
