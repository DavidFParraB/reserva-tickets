package meli.reserva.tickets.model;

import java.util.List;

import com.google.cloud.firestore.annotation.DocumentId;

import lombok.Data;

@Data
public class Show {

    @DocumentId
    private String showId;
    private String fechaPresentacion;
    private List<Ubicacion> ubicacion;
}
