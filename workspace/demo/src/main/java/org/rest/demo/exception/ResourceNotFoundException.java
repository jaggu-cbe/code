package org.rest.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
        super("requested resource not found");
    }
    
	public ResourceNotFoundException(String message) {
        super(message);
    }
}
