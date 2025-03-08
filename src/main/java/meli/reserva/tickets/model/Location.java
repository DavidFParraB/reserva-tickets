package meli.reserva.tickets.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {

	private String name;
	private Float price;
	private int capacity;
	private int available;
	private List<Seat> seats;
	//Reference
	private String showId;
}
