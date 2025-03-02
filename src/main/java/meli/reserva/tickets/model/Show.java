package meli.reserva.tickets.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import lombok.Data;

@Data
@Document(collectionName = "shows")
public class Show {

	@DocumentId
	private String show;

	@JsonProperty("fecha_presentacion")
	private String fechaPresentacion;
	private List<Ubicacion> ubicacion;
}
