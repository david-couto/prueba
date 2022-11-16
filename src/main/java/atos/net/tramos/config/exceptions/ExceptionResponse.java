package atos.net.tramos.config.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
	private String errorCode;
	private List<ExceptionResponseError> errors;
}
