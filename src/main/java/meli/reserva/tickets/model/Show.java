package meli.reserva.tickets.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Show {
	
	private String show;
	@JsonProperty("date_presentation")
	private String datePresentation;
	private List<Location> locations;
}
