package org.rest.demo.exception;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidInputException() {
        super("Invalid Input");
    }
    
	public InvalidInputException(String msg) {
        super(msg);
    }
}
