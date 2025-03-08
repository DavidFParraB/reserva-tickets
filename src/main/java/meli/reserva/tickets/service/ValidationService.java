package meli.reserva.tickets.service;

public interface ValidationService {
	Boolean validateUser(String userToken);
	
	Boolean validateAppUser (String appUserToken);
}
