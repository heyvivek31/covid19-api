package com.covid19.plasma.exception;

public class UnknownException extends PlasmaException {
    public UnknownException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public UnknownException(String errorMessage) {
        super(errorMessage);
    }
}
