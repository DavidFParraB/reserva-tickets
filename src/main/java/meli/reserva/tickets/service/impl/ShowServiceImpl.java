package meli.reserva.tickets.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.repository.LocationRepository;
import meli.reserva.tickets.repository.ShowRepository;
import meli.reserva.tickets.service.ShowService;

@Service
public class ShowServiceImpl implements ShowService {

	private ShowRepository showRepository;
	private LocationRepository locationRepository;

	public ShowServiceImpl(ShowRepository showRepository, LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
		this.showRepository = showRepository;
	}

	@Override
	public Boolean saveShow(Show show) {
		Boolean response = Boolean.FALSE;

		showRepository.saveShow(show);

		if (show.getLocations() != null) {
			locationRepository.saveLocation(show.getLocations(), show.getShow());
		}
		response = Boolean.TRUE;
		return response;
	}

	@Override
	public List<Show> getShowsByFilter(Filter filter) {
		return showRepository.findShowsByFilters(filter);
	}

	@Override
	public Show getShowById(String showId) {
		return showRepository.findById(showId);
	}

}
