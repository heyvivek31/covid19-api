package com.covid19.plasma.exception;

public class TooManyRequestException extends PlasmaException {
    public TooManyRequestException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public TooManyRequestException(String errorMessage) {
        super(errorMessage);
    }
}
