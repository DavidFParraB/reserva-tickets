package meli.reserva.tickets.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.model.Book;
import meli.reserva.tickets.service.BookService;
import meli.reserva.tickets.service.ValidationService;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
public class ReservaController {

	private final BookService reservaService;
	private final ValidationService validateService;

	public ReservaController(BookService reservaService, ValidationService validateService) {
		this.reservaService = reservaService;
		this.validateService = validateService;
	}

	@PostMapping("/reservar")
	public Mono<String> reservar(@RequestBody Book bookIn, @RequestHeader("Authorization") String authorizationHeader)
			throws ExecutionException, InterruptedException {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return Mono.just("AUTHORIZATION_NOT_FOUND");
		}

		String token = authorizationHeader.substring(7);
		log.info("Token in: {}", token);
		if (Boolean.FALSE.equals(validateService.validateAppUser(token))) {
			return Mono.just("INVALID_TOKEN");
		}

		return reservaService.reservaTickets(bookIn);
	}
}
