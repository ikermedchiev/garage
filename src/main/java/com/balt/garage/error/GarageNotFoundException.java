package com.balt.garage.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No garages")
public class GarageNotFoundException extends RuntimeException {
    public GarageNotFoundException(String message) {
        super(message);
    }
}
