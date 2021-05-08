package com.covid19.plasma.exception;

public class PlasmaException extends RuntimeException {
    public PlasmaException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public PlasmaException(String errorMessage) {
        super(errorMessage);
    }
}
