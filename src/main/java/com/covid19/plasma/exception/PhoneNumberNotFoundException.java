package com.covid19.plasma.exception;

public class PhoneNumberNotFoundException extends PlasmaException {
    public PhoneNumberNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public PhoneNumberNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
