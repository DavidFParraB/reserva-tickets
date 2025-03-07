package meli.reserva.tickets.service.impl;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.repository.ShowRepository;
import meli.reserva.tickets.repository.impl.ShowRepositoryImpl;
import meli.reserva.tickets.service.ShowService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class ShowServiceImpl implements ShowService {

	private ShowRepository showRepository;
	private ShowRepositoryImpl showRepositoryImpl;

	public ShowServiceImpl(ShowRepository showRepository, ShowRepositoryImpl showRepositoryImpl) {
		this.showRepository = showRepository;
		this.showRepositoryImpl = showRepositoryImpl;
	}

	@Override
	public Flux<Show> getAllShows() {
		return showRepository.findAll();
	}

	@Override
	public Mono<Show> findShowById(String show) {
		return showRepository.findByShow(show);
	}

	@Override
	public void saveShow(Show show) {
		log.info("Saving show: {}", show);

		showRepository.save(show).subscribe();
	}

	@Override
	public Flux<Show> findShowsByFecha(String fechaInicio, String fechaFin) {
		return showRepositoryImpl.findShowsByFecha(fechaInicio, fechaFin);
	}

}
