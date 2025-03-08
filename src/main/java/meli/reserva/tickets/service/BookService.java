package meli.reserva.tickets.service;

import java.util.concurrent.ExecutionException;

import meli.reserva.tickets.model.Book;
import reactor.core.publisher.Mono;

public interface BookService {
	Mono<Boolean> reservaTickets(Book bookIn) throws ExecutionException, InterruptedException;
}
