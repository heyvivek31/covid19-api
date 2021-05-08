package com.covid19.plasma.exception;

public class CovidStatsException extends RuntimeException {
    public CovidStatsException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CovidStatsException(String errorMessage) {
        super(errorMessage);
    }
}