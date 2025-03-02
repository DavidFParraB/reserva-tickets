package meli.reserva.tickets.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meli.reserva.tickets.model.Butaca;
import meli.reserva.tickets.model.Ubicacion;
import meli.reserva.tickets.repository.ShowRepository;
import reactor.core.publisher.Mono;

@Service
public class RerservaServiceImpl {

	private ShowRepository showRepository;

	public RerservaServiceImpl(ShowRepository showRepository) {
		this.showRepository = showRepository;
	}

	@Transactional
	public Mono<Boolean> reservarButaca(String showId, String ubicacionNombre, String nroSilla) {
		return showRepository.findByShow(showId).flatMap(show -> {
			Ubicacion ubicacion = show.getUbicacion().stream().filter(u -> u.getNombre().equals(ubicacionNombre))
					.findFirst().orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

			Butaca butaca = ubicacion.getButacas().stream().filter(b -> b.getNroSilla().equals(nroSilla)).findFirst()
					.orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

			if (!butaca.isDisponible()) {
				return Mono.error(new RuntimeException("Butaca no disponible"));
			}

			butaca.setDisponible(false);
			ubicacion.setDisponibles(ubicacion.getDisponibles() - 1);

			return showRepository.save(show).map(showActualizado -> {
				// Registrar reserva. Si se requiere. 
				
				return true;
			});
		});
	}
}
