package meli.reserva.tickets.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import meli.reserva.tickets.service.ValidationService;

@Service
@Log4j2
public class ValidationServiceImpl implements ValidationService {

	@Value("${token.user}")
	private String tokenUser;

	@Value("${token.app}")
	private String tokenApp;

	@Override
	public Boolean validateAppUser(String appUserToken) {
		log.info("Token del app: {} " , tokenApp);
		return tokenApp.equals(appUserToken);
	}

	@Override
	public Boolean validateUser(String userToken) {
		return tokenUser.equals(userToken);
	}
}
