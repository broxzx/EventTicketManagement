package com.example.eventticketmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TicketWasBoughtException extends RuntimeException{

    public TicketWasBoughtException(String message) {
        super(message);
    }
}
