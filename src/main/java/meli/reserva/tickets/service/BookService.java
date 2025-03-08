package meli.reserva.tickets.service;

import java.util.concurrent.ExecutionException;

import meli.reserva.tickets.model.Book;
import reactor.core.publisher.Mono;

public interface BookService {
	Mono<String> reservaTickets(Book bookIn) throws ExecutionException, InterruptedException;
}
