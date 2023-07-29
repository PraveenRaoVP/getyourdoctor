package com.getyourdoc.getyourdoctors.exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
