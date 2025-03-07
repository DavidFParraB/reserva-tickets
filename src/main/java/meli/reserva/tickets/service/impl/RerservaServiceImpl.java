package meli.reserva.tickets.service.impl;

import java.sql.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.model.Book;
import meli.reserva.tickets.model.Butaca;
import meli.reserva.tickets.model.Ubicacion;
import meli.reserva.tickets.repository.BookRepository;
import meli.reserva.tickets.repository.ShowRepository;
import meli.reserva.tickets.service.ReservaService;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class RerservaServiceImpl implements ReservaService {

	private ShowRepository showRepository;
	private BookRepository bookRepository;

	public RerservaServiceImpl(ShowRepository showRepository, BookRepository bookRepository) {
		this.showRepository = showRepository;
		this.bookRepository = bookRepository;
	}

	@Transactional
	public Mono<Boolean> reservaTickets(Book bookIn) {
		return showRepository.findByShow(bookIn.getShow()).flatMap(show -> {
			Ubicacion ubicacion = show.getUbicacion().stream().filter(u -> u.getNombre().equals(bookIn.getLocation()))
					.findFirst().orElseThrow(() -> new RuntimeException("SEAT_NOT_FOUND"));

			Butaca butaca = ubicacion.getButacas().stream().filter(b -> b.getNroSilla().equals(bookIn.getSeatNumber()))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("SEAT_NOT_FOUND"));

			if (!butaca.isDisponible()) {
				return Mono.error(new RuntimeException("SEAT_NOT_AVAILABLE"));
			}

			butaca.setDisponible(false);
			ubicacion.setDisponibles(ubicacion.getDisponibles() - 1);

			return showRepository.save(show).map(showActualizado -> {
				Book newBook = new Book();
				newBook.setId(UUID.randomUUID().toString());
				newBook.setDni(bookIn.getDni());
				newBook.setName(bookIn.getName());
				newBook.setShow(bookIn.getShow());
				newBook.setLocation(bookIn.getLocation());
				newBook.setSeatNumber(bookIn.getSeatNumber());
				newBook.setPrice(ubicacion.getPrecio());
				newBook.setBookDate(new Date(System.currentTimeMillis()));
				bookRepository.save(newBook).subscribe();

				return true;
			});
		});
	}
}
