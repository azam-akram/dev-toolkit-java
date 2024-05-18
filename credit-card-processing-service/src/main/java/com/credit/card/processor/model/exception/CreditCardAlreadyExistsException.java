package com.credit.card.processor.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CreditCardAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CreditCardAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CreditCardAlreadyExistsException(String message) {
        super(message);
    }
}