package com.covid19.plasma.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class ApiError {
    
    private int statusCode;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    Map<String, Set<String>> errorsMap;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.errorsMap = new HashMap<>();
    }

    public ApiError(String message, Throwable ex) {
        this();
        this.message = message;
        this.errorsMap = new HashMap<>();
    }

    public ApiError(int statusCode, String status, String message, Throwable ex) {
        this();
        this.message = message;
        this.statusCode= statusCode;
        this.status= status;
        this.errorsMap = new HashMap<>();
    }
}
