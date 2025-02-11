package com.fsv.rest.webservices.restfulwebservices.handlers;

import com.fsv.rest.webservices.restfulwebservices.exception.ErrorResponse;
import com.fsv.rest.webservices.restfulwebservices.exception.NotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/*@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	*/

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse(true, Collections.singletonList(new ErrorResponse.ErrorDetail("Internal Server Error", ex.getMessage())), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFound.class)
    public final ResponseEntity<ErrorResponse> handleNotFound(Exception ex, WebRequest request) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse(true, Collections.singletonList(new ErrorResponse.ErrorDetail("Error Not Found", ex.getMessage())), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ErrorResponse.ErrorDetail> errorDetails = new ArrayList<>();

        ex.getFieldErrors().forEach(fieldError -> {
            errorDetails.add(new ErrorResponse.ErrorDetail("Bad Request", fieldError.getDefaultMessage()));
        });
        ErrorResponse errorResponse = new ErrorResponse(true, errorDetails, null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}