package com.n26.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	
	public ValidationErrorException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
 	
}
