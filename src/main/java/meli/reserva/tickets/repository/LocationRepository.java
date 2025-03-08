package meli.reserva.tickets.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Location;

@Repository
public interface LocationRepository {
	void saveLocation(List<Location> locations, String showId);

	List<Location> locationsByShowAndFilter(String showId, Filter filter);
}