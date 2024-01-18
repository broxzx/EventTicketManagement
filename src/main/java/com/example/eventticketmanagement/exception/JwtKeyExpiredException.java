package com.example.eventticketmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class JwtKeyExpiredException extends RuntimeException{
    public JwtKeyExpiredException(String message) {
        super(message);
    }
}
