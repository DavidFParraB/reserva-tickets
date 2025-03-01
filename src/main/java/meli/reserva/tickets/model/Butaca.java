package meli.reserva.tickets.model;

import lombok.Data;

@Data
public class Butaca {

    private String nroSilla;
    private int valor;
    private boolean disponible;
}