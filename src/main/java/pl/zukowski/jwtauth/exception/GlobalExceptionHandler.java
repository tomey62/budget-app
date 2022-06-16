package pl.zukowski.jwtauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception exception, WebRequest webRequest) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<?> globalException(ResourceExistException exception, WebRequest webRequest) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.CONFLICT);
    }
}
