package meli.reserva.tickets.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import meli.reserva.tickets.Utils;
import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Location;
import meli.reserva.tickets.repository.LocationRepository;
import meli.reserva.tickets.repository.SeatRepository;

@Service
public class LocationRepositoryImpl implements LocationRepository {

	private Firestore firestore;
	private SeatRepository seatRepository;

	public LocationRepositoryImpl(@Autowired Firestore firestore, SeatRepository seatRepository) {
		this.firestore = firestore;
		this.seatRepository = seatRepository;
	}

	@Override
	public void saveLocation(List<Location> locations, String showId) {
		try {
			for (Location location : locations) {
				location.setShowId(showId);
				ApiFuture<WriteResult> future = firestore.collection(Utils.SHOWS_COLLECTION).document(showId)
						.collection(Utils.LOCATION_COLLECTION).document(location.getName()).set(location);
				future.get();

				if (location.getSeats() != null) {
					seatRepository.saveSeats(location.getSeats(), showId, location.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Location> locationsByShowAndFilter(String showId, Filter filter) {
		List<Location> locationes = new ArrayList<Location>();
		try {
			CollectionReference locationsRef = firestore.collection(Utils.SHOWS_COLLECTION).document(showId)
					.collection(Utils.LOCATION_COLLECTION);
			Query query = locationsRef;

			if (!Objects.isNull(filter)) {
				if (filter.getInitPrice() != null) {
					query = query.whereGreaterThanOrEqualTo(Utils.FIELD_PRICE, filter.getInitPrice());
				}
				if (filter.getEndPrice() != null) {
					query = query.whereLessThanOrEqualTo(Utils.FIELD_PRICE, filter.getEndPrice());
				}
			}
			ApiFuture<QuerySnapshot> future = query.get();

			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				Location location = document.toObject(Location.class);
				location.setSeats(seatRepository.seatsByLocation(showId, document.getId()));
				locationes.add(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationes;
	}

}
