package com.example.eventticketmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserWithResourceNotFound extends RuntimeException {
    public UserWithResourceNotFound(String message) {
        super(message);
    }
}
