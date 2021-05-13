package com.covid19.plasma.exception;

public class InvalidPhoneNumberException  extends PlasmaException {
    public InvalidPhoneNumberException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public InvalidPhoneNumberException(String errorMessage) {
        super(errorMessage);
    }
}
