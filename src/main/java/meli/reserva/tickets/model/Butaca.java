package meli.reserva.tickets.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Butaca {

    @JsonProperty("nro_silla")
    private String nroSilla;
    private int valor;
    private boolean disponible;
}