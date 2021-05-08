package com.covid19.plasma.exception;

public class PlasmaRequestorNotFound extends PlasmaException{
    public PlasmaRequestorNotFound(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public PlasmaRequestorNotFound(String errorMessage) {
        super(errorMessage);
    }
}
