package com.getyourdoc.getyourdoctors.exceptions;

public class AppointmentAlreadyBookedException extends RuntimeException {
    public AppointmentAlreadyBookedException(String message) {
        super(message);
    }
}

