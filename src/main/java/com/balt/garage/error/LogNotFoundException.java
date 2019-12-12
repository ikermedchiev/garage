package com.balt.garage.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No logs")
public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException(String message) {
        super(message);
    }
}
