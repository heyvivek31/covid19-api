package com.covid19.plasma.exception;

public class DuplicatePhoneNumberFoundException extends PlasmaException {
    public DuplicatePhoneNumberFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public DuplicatePhoneNumberFoundException(String errorMessage) {
        super(errorMessage);
    }
}
