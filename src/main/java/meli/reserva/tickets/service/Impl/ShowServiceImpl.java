package meli.reserva.tickets.service.Impl;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.repository.ShowRepository;
import meli.reserva.tickets.service.ShowService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class ShowServiceImpl implements ShowService {

	public ShowServiceImpl(ShowRepository showRepository) {
		this.showRepository = showRepository;
	}

	private ShowRepository showRepository;

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

}
