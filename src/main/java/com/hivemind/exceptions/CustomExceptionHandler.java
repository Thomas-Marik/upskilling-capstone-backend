package com.hivemind.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> UserExceptionHandler(CustomerNotFoundException customerNotFoundException) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = customerNotFoundException.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(status, message);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<ErrorMessage> UserExceptionHandler(TransactionNotFoundException transactionNotFoundException) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = transactionNotFoundException.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(status, message);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> UserExceptionHandler(AccountNotFoundException accountNotFoundException) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = accountNotFoundException.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(status, message);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<ErrorMessage> UserExceptionHandler(InsufficientFundsException insufficientFundsException) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = insufficientFundsException.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(status, message);
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
