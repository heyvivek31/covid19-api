package com.covid19.plasma.exception;

public class TokenException extends PlasmaException {
    public TokenException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public TokenException(String errorMessage) {
        super(errorMessage);
    }
}
