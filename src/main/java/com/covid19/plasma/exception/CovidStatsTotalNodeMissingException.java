package com.covid19.plasma.exception;

public class CovidStatsTotalNodeMissingException extends CovidStatsException {
    public CovidStatsTotalNodeMissingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CovidStatsTotalNodeMissingException(String errorMessage) {
        super(errorMessage);
    }
}
