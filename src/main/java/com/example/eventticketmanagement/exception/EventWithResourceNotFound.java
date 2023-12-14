package com.example.eventticketmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventWithResourceNotFound extends RuntimeException{
    public EventWithResourceNotFound(String message) {
        super(message);
    }
}
