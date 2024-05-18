package com.azamakram.github.BookStore.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BookNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}