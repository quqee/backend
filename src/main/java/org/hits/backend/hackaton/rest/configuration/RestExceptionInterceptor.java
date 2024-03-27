package org.hits.backend.hackaton.rest.configuration;

import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionInterceptor extends ResponseEntityExceptionHandler {
    private static final Map<ExceptionType, HttpStatus> STATUS_MAP = Map.of(
            ExceptionType.INVALID, HttpStatus.BAD_REQUEST,
            ExceptionType.ALREADY_EXISTS, HttpStatus.CONFLICT,
            ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND,
            ExceptionType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED
    );

    @ExceptionHandler(value = {ExceptionInApplication.class})
    protected ResponseEntity<Object> handleException(ExceptionInApplication ex, WebRequest request) {
        final HttpStatus status = STATUS_MAP.get(ex.getType());

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return handleExceptionInternal(ex, getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        final String exceptionBody = "Internal server error";
        return handleExceptionInternal(ex, exceptionBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
