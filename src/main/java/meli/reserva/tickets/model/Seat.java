package meli.reserva.tickets.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seat {
	@JsonProperty("seat_number")
	private String seatNumber;
	private Boolean available;

	private String showId;
	private String locationId;

}
