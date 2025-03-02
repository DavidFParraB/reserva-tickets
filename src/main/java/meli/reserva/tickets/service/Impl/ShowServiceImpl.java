package meli.reserva.tickets.service.Impl;

import org.springframework.stereotype.Service;

import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.repository.ShowRepository;
import meli.reserva.tickets.service.ShowService;
import reactor.core.publisher.Flux;

@Service
public class ShowServiceImpl implements ShowService {

	public ShowServiceImpl(ShowRepository showRepository) {
		this.showRepository = showRepository;
	}

	private ShowRepository showRepository;

	@Override
	public Flux<Show> getAllShows() {
		return showRepository.findAll();
	}

}
