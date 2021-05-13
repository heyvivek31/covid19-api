package com.covid19.plasma.exception;

import com.covid19.plasma.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );
        return new ResponseEntity(errorsMap.isEmpty() ? ex : errorsMap, headers, status);
    }

    @ExceptionHandler(value = {PlasmaException.class, TokenException.class, TooManyRequestException.class})
    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        if (ex instanceof TokenException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof TooManyRequestException) {
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        } else if (ex instanceof DuplicatePhoneNumberFoundException) {
            httpStatus = HttpStatus.CONFLICT;
        }
        return buildResponseEntity(new ApiError(httpStatus, ex.getMessage(), ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
