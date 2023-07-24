package com.getyourdoc.getyourdoctors.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;


public class AppointmentNotAvailable extends RuntimeException{
    public AppointmentNotAvailable(String message) {
        super(message);
    }
}
