package atos.net.tramos.config.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TalgoCustomExceptionTest {

	public List<ExceptionResponseError> createListExceptionResponseError(){
		List<ExceptionResponseError> exResErrorList = new ArrayList<>();
		exResErrorList.add(new ExceptionResponseError("campo 1", "error 1"));
		exResErrorList.add(new ExceptionResponseError("campo 2", "error 2"));
		return exResErrorList;
	}
	
	public ExceptionResponse createExceptionResponse() {
		ExceptionResponse exceptionResponse = new ExceptionResponse("errorCode1", createListExceptionResponseError());
		return exceptionResponse;
	}
	
	@Test
	void ValidConstructorWithExceptionResponseAndHttpStatus(){
		
		
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
