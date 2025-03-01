package meli.reserva.tickets.model;

import java.util.List;

import lombok.Data;

@Data
public class Ubicacion {
    private String nombre;
    private int precio;
    private int capacidad;
    private int disponibles;
    private List<Butaca> butacas;
}