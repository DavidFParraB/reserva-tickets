package meli.reserva.tickets.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.service.ShowService;
import meli.reserva.tickets.service.ValidationService;

@RestController
public class ShowController {

	private final ShowService showService;
	private final ValidationService validationService;

	public ShowController(ShowService showService, ValidationService validationService) {
		this.showService = showService;
		this.validationService = validationService;
	}

	@GetMapping("/show")
	public ResponseEntity<List<Show>> getShows(@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return new ResponseEntity<List<Show>>(HttpStatus.UNAUTHORIZED);
		}

		String token = authorizationHeader.substring(7);
		if (!validationService.validateUser(token)) {
			return new ResponseEntity<List<Show>>(HttpStatus.UNAUTHORIZED);
		}

		return ResponseEntity.ok(showService.getShowsByFilter(null));
	}

	@GetMapping("/show/{showId}")
	public ResponseEntity<Show> showById(@PathVariable String showId,
			@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return new ResponseEntity<Show>(HttpStatus.UNAUTHORIZED);
		}

		String token = authorizationHeader.substring(7);
		if (!validationService.validateUser(token)) {
			return new ResponseEntity<Show>(HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(showService.getShowById(showId));
	}

	@PostMapping(("/shows"))
	public ResponseEntity<Boolean> fillShows(@RequestBody Show show,
			@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);
		}

		String token = authorizationHeader.substring(7);
		if (!validationService.validateUser(token)) {
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);
		}

		return ResponseEntity.ok(showService.saveShow(show));
	}

	@PostMapping("/shows/filter")
	public ResponseEntity<List<Show>> getShowsByFilter(@RequestBody Filter filter,
			@RequestHeader("Authorization") String authorizationHeader) {

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return new ResponseEntity<List<Show>>(HttpStatus.UNAUTHORIZED);
		}

		String token = authorizationHeader.substring(7);
		if (!validationService.validateUser(token)) {
			return new ResponseEntity<List<Show>>(HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(showService.getShowsByFilter(filter));
	}
}
