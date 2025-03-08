package meli.reserva.tickets.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import meli.reserva.tickets.Utils;
import meli.reserva.tickets.model.Seat;
import meli.reserva.tickets.repository.SeatRepository;

@Service
public class SeatRepositoryImpl implements SeatRepository {

	private Firestore firestore;

	public SeatRepositoryImpl(@Autowired Firestore firestore) {
		this.firestore = firestore;
	}

	@Override
	public void saveSeats(List<Seat> seats, String showId, String locationId) {

		try {
			for (Seat seat : seats) {
				seat.setShowId(showId);
				seat.setLocationId(locationId);
				ApiFuture<WriteResult> future = firestore.collection(Utils.SHOWS_COLLECTION).document(showId)
						.collection(Utils.LOCATION_COLLECTION).document(locationId)
						.collection(Utils.SEAT_COLLECTION).document(seat.getSeatNumber()).set(seat);
				future.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Seat> seatsByLocation(String showId, String locationId) {
		List<Seat> seats = new ArrayList<Seat>();
		try {
			ApiFuture<QuerySnapshot> future = firestore.collection("shows")
					.document(showId).collection(Utils.LOCATION_COLLECTION).document(locationId)
					.collection(Utils.SEAT_COLLECTION).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				seats.add(document.toObject(Seat.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seats;
	}

}
