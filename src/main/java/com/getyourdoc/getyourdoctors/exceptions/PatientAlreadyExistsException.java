package com.getyourdoc.getyourdoctors.exceptions;

public class PatientAlreadyExistsException extends RuntimeException{
    public PatientAlreadyExistsException(String message) {
        super(message);
    }
}
