package atos.net.tramos.config.exceptions.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import atos.net.tramos.config.exceptions.ExceptionResponse;
import atos.net.tramos.config.exceptions.ExceptionResponseError;
import atos.net.tramos.config.exceptions.TalgoCustomException;

class ExceptionControllerTest {

	private ExceptionController exceptionController = new ExceptionController();
	
	@Test
	public void TalgoCustomExceptionWithExceptionResponseError() {
		ExceptionResponseError exResponseError = new ExceptionResponseError("Metodo 1", "Acceso no permitido");
		TalgoCustomException talgoException = new TalgoCustomException(exResponseError, HttpStatus.METHOD_NOT_ALLOWED);
		System.out.println(talgoException.getExceptionResponse());
		System.out.println(exceptionController.invalidInput(talgoException));
	}

}
