package com.markusdel.cloudparking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException {

    public ParkingNotFoundException(String id){
        super("Parking not found with Id: "+id);
    }
}
