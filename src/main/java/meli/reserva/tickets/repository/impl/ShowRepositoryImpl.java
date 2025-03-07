package meli.reserva.tickets.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.model.Show;
import reactor.core.publisher.Flux;

@Repository
@Log4j2
public class ShowRepositoryImpl {

    private Firestore firestore;

    public ShowRepositoryImpl(@Autowired Firestore firestore) {
        this.firestore = firestore;
    }

    private static final String COLLECTION_NAME = "shows";
    private static final String FIELD_FECHA_PRESENTACION = "fechaPresentacion";
    private static final String FIELD_PRICE = "ubicacion.precio";

    public Flux<Show> findShowsByFecha(String fechaInicio, String fechaFin) {

        log.info("Rango de busqueda: {} - {}", fechaInicio, fechaFin);
        log.info("ProjectId {} - DatabaseId : {}", firestore.getOptions().getProjectId(),
                firestore.getOptions().getDatabaseId());
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(FIELD_FECHA_PRESENTACION, fechaInicio)
                .whereLessThanOrEqualTo(FIELD_FECHA_PRESENTACION, fechaFin)
                // .whereGreaterThanOrEqualTo(FIELD_PRICE, 50)
                // .whereLessThanOrEqualTo(FIELD_PRICE, 2000)
                .orderBy(FIELD_FECHA_PRESENTACION, Query.Direction.ASCENDING)
                .get();
        try {
            QuerySnapshot querySnapshot = future.get();
            return Flux.fromIterable(querySnapshot.getDocuments()).map(document -> document.toObject(Show.class));
        } catch (Exception e) {
            log.error("Error fetching shows: {}", e.getMessage());
            return Flux.error(e);
        }
    }
}