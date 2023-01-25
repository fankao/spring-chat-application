package com.chat.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadAuthException extends RuntimeException{
    public BadAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
