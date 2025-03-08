package meli.reserva.tickets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Filter {

	@JsonProperty("init_date")
	private String initDate;
	@JsonProperty("end_date")
	private String endDate;
	@JsonProperty("init_price")
	private Float initPrice;
	@JsonProperty("end_price")
	private Float endPrice;
	private String order;
}
