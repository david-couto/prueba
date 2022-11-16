package atos.net.tramos.config.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalgoCustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1499208272942678033L;

	private final ExceptionResponse exceptionResponse;
	private final HttpStatus httpStatus;

	public TalgoCustomException(ExceptionResponse exceptionResponse, HttpStatus httpStatus) {

		this.exceptionResponse = exceptionResponse;
		this.httpStatus = httpStatus;
	}

	public TalgoCustomException(ExceptionResponseError exceptionResponseError, HttpStatus httpStatus) {
		List<ExceptionResponseError> validErrors = new ArrayList<>();
		validErrors.add(exceptionResponseError);
		this.exceptionResponse = new ExceptionResponse("validation_errors", validErrors);
		this.httpStatus = httpStatus;
	}

	public TalgoCustomException(String errorCode, ExceptionResponseError exceptionResponseError, HttpStatus status) {
		List<ExceptionResponseError> validErrors = new ArrayList<>();
		validErrors.add(exceptionResponseError);

		exceptionResponse = new ExceptionResponse(errorCode, validErrors);
		httpStatus = status;
	}

	public TalgoCustomException(String errorCode, List<ExceptionResponseError> exceptions, HttpStatus status) {
		exceptionResponse = new ExceptionResponse(errorCode, exceptions);
		httpStatus = status;
	}

	public TalgoCustomException(String errorCode, String field, String error, HttpStatus status) {
		List<ExceptionResponseError> validErrors = new ArrayList<>();
		validErrors.add(new ExceptionResponseError(field, error));

		exceptionResponse = new ExceptionResponse(errorCode, validErrors);
		httpStatus = status;
	}
}
