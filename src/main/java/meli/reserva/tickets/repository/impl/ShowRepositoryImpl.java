package meli.reserva.tickets.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.Utils;
import meli.reserva.tickets.dto.Filter;
import meli.reserva.tickets.model.Show;
import meli.reserva.tickets.repository.LocationRepository;
import meli.reserva.tickets.repository.ShowRepository;

@Repository
@Log4j2
public class ShowRepositoryImpl implements ShowRepository {

	private Firestore firestore;
	private LocationRepository locationRepository;

	public ShowRepositoryImpl(@Autowired Firestore firestore, LocationRepository locationRepository) {
		this.firestore = firestore;
		this.locationRepository = locationRepository;
	}

	@Override
	public List<Show> findShowsByFilters(Filter filter) {
		List<Show> shows = new ArrayList<Show>();
		try {
			CollectionReference locationsRef = firestore.collection(Utils.SHOWS_COLLECTION);
			Query query = locationsRef;

			if (!Objects.isNull(filter)) {

				if (filter.getInitDate() != null) {
					query = query.whereGreaterThanOrEqualTo(Utils.FIELD_FECHA_PRESENTACION, filter.getInitDate());
				}
				if (filter.getEndDate() != null) {
					query = query.whereLessThanOrEqualTo(Utils.FIELD_FECHA_PRESENTACION, filter.getEndDate());
				}
				if (filter.getOrder() != null) {
					if (filter.getOrder().equalsIgnoreCase("ASC")) {
						query = query.orderBy(Utils.FIELD_FECHA_PRESENTACION, Query.Direction.ASCENDING);
					} else {
						query = query.orderBy(Utils.FIELD_FECHA_PRESENTACION, Query.Direction.DESCENDING);
					}
				}
			}
			ApiFuture<QuerySnapshot> future = query.get();

			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {

				Show show = document.toObject(Show.class);
				show.setLocations(locationRepository.locationsByShowAndFilter(show.getShow(), filter));
				shows.add(show);
			}
		} catch (Exception e) {
			log.error("Error consulta con filtro: ", e);
		}
		return shows;
	}

	@Override
	public void saveShow(Show show) {

		try {
			ApiFuture<WriteResult> future = firestore.collection(Utils.SHOWS_COLLECTION).document(show.getShow())
					.set(show);
			future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Show findById(String showId) {
		DocumentReference documentReference = firestore.collection(Utils.SHOWS_COLLECTION).document(showId);
		try {
			ApiFuture<DocumentSnapshot> future = documentReference.get();
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				return document.toObject(Show.class);
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}
}