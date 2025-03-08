package meli.reserva.tickets.service.impl;

import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.model.Book;
import meli.reserva.tickets.model.Location;
import meli.reserva.tickets.model.Seat;
import meli.reserva.tickets.repository.BookRepository;
import meli.reserva.tickets.service.BookService;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class BookServiceImpl implements BookService {

	private Firestore firestore;
	private BookRepository bookRepository;

	public BookServiceImpl(Firestore firestore, BookRepository bookRepository) {
		this.firestore = firestore;
		this.bookRepository = bookRepository;
	}

	@Transactional
	public Mono<Boolean> reservaTickets(Book bookIn) throws ExecutionException, InterruptedException {
		ApiFuture<Boolean> transactionResult = firestore.runTransaction(transaction -> {
			try {

				DocumentReference seatRef = firestore.collection("shows").document(bookIn.getShow())
						.collection("locations").document(bookIn.getLocation()).collection("seats")
						.document(bookIn.getSeatNumber());
				DocumentSnapshot seatSnapshot = transaction.get(seatRef).get();
				Seat seat = seatSnapshot.toObject(Seat.class);

				if (seat != null && seat.getAvailable()) {
					log.info("Seat: {} - available: {}", seat.getSeatNumber(), seat.getAvailable());
					DocumentReference locationRef = firestore.collection("shows").document(bookIn.getShow())
							.collection("locations").document(bookIn.getLocation());

					DocumentSnapshot locationSnapshot = transaction.get(locationRef).get();
					Location location = locationSnapshot.toObject(Location.class);

					if (location == null) {
						log.error("Location not found for ID: {}", bookIn.getLocation());
						return false;
					}
					seat.setAvailable(Boolean.FALSE);
					transaction.set(seatRef, seat);

					location.setAvailable(location.getAvailable() - 1);
					transaction.set(locationRef, location);
					log.info("Location: {} - disponibles: {}", location.getName(), location.getAvailable());

					doBook(bookIn, location);

					return true; // Reserva exitosa
				} else {
					log.warn("Seat not available or not found: {}", bookIn.getSeatNumber());
					return false; // Butaca no disponible
				}
			} catch (Exception e) {
				log.error("Transaction failed: ", e);
				return false;
			}
		});

		return Mono.just(transactionResult.get());
	}

	void doBook(Book bookIn, Location location) {
		Book newBook = new Book();
		newBook.setId(UUID.randomUUID().toString());
		newBook.setDni(bookIn.getDni());
		newBook.setName(bookIn.getName());
		newBook.setShow(bookIn.getShow());
		newBook.setLocation(bookIn.getLocation());
		newBook.setSeatNumber(bookIn.getSeatNumber());
		newBook.setPrice(location.getPrice());
		newBook.setBookDate(new Date(System.currentTimeMillis()));
		bookRepository.save(newBook).subscribe();
	}

}
