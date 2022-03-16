package org.rest.demo.exception;

import org.rest.demo.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ResponseEntityExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Response> handleResourceNotFoundEx(ResourceNotFoundException ex){
    	logger.error("Resource Not Found Exception: ", ex);		
		Response res = new Response();
		res.setStatus(HttpStatus.NOT_FOUND.value());
		res.setError(ex.getMessage());		
	    return new ResponseEntity<Response>(res, HttpStatus.NOT_FOUND);
    }

    
	@ExceptionHandler(value = { InvalidInputException.class, MethodArgumentNotValidException.class })
	public ResponseEntity<Response> handleInvalidInputEx(Exception ex) {
		logger.error("Invalid Input Exception: ", ex);		
		Response res = new Response();
		res.setStatus(HttpStatus.BAD_REQUEST.value());
		res.setError(ex.getMessage());		
	    return new ResponseEntity<Response>(res, HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> handleException(Exception ex){
    	logger.error("Exception: ", ex);		
		Response res = new Response();
		res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		res.setError(ex.getMessage());
	    return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}