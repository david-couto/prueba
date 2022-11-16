package atos.net.tramos.config.exceptions.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import atos.net.tramos.config.exceptions.ExceptionResponse;
import atos.net.tramos.config.exceptions.ExceptionResponseError;
import atos.net.tramos.config.exceptions.TalgoCustomException;


@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> invalidInput(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		ExceptionResponse exceptionResponse = new ExceptionResponse("validation_errors", fromBindingErrors(result));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ExceptionResponse> invalidInput(MissingServletRequestParameterException ex) {
		ExceptionResponseError error = new ExceptionResponseError(ex.getParameterName(), "required");
		ExceptionResponse exceptionResponse = new ExceptionResponse("validation_errors", Arrays.asList(error));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> invalidInput(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> result = ex.getConstraintViolations();
		ExceptionResponse exceptionResponse = new ExceptionResponse("validation_errors", fromBindingErrors(result));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(TalgoCustomException.class)
	public ResponseEntity<ExceptionResponse> invalidInput(TalgoCustomException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getExceptionResponse());
	}

	private List<ExceptionResponseError> fromBindingErrors(Errors errors) {

		List<ExceptionResponseError> validErrors = new ArrayList<>();
		for (FieldError objectError : errors.getFieldErrors()) {
			String errorCode = this.camelToSnake(objectError.getCodes()[objectError.getCodes().length - 1]);
			validErrors.add(new ExceptionResponseError(objectError.getField(), errorCode));
		}
		return validErrors;
	}

	private List<ExceptionResponseError> fromBindingErrors(Set<ConstraintViolation<?>> errors) {
		List<ExceptionResponseError> validErrors = new ArrayList<>();
		for (ConstraintViolation<?> objectError : errors) {
			String errorCode = objectError.getMessage();

			String errorFieldPath = objectError.getPropertyPath().toString();
			String errorField = errorFieldPath.substring(errorFieldPath.lastIndexOf('.') + 1);

			validErrors.add(new ExceptionResponseError(errorField, errorCode));
		}
		return validErrors;
	}

	private String camelToSnake(String str) {
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";
		str = str.replaceAll(regex, replacement).toLowerCase();
		return str;
	}
}
