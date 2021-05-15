package com.covid19.plasma.exception;

public class PhoneNumberAlreadyExistsException extends PlasmaException {
    public PhoneNumberAlreadyExistsException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public PhoneNumberAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
