package com.covid19.plasma.exception;

public class PlasmaDonorNotFound extends PlasmaException {
    public PlasmaDonorNotFound(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public PlasmaDonorNotFound(String errorMessage) {
        super(errorMessage);
    }
}
