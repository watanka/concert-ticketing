package hhplus.ticketing.base.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
//
//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<String> handleInvalidTokenException(InsufficientBalanceException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

}