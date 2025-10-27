package dev.tarasshablii.opora.microservices.initiatives.endpoint.rest;

import dev.tarasshablii.opora.microservices.initiatives.domain.exception.NotFoundException;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.dto.ErrorResponseDto;
import dev.tarasshablii.opora.microservices.initiatives.endpoint.rest.dto.ErrorResponseErrorsInnerDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Slf4j
@RestControllerAdvice
public class InitiativesExceptionHandler {

    private static final String INVALID_REQUEST_BODY = "Invalid request body";
    private static final String REQUEST_FAILED_VALIDATION = "Request failed validation";

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadable(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto()
                        .timestamp(Instant.now())
                        .message(INVALID_REQUEST_BODY)
                        .details(exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        var errors = Collections.singletonList(
                new ErrorResponseErrorsInnerDto()
                        .field(exception.getPropertyName())
                        .message(exception.getMessage()));
        ErrorResponseDto responseDto = new ErrorResponseDto()
                .timestamp(Instant.now())
                .message("Type mismatch occurred, see errors for details")
                .errors(errors);
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {

        log.debug(exception.getMessage(), exception);
        var errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponseErrorsInnerDto()
                        .field(error.getField())
                        .message(error.getDefaultMessage()))
                .toList();
        return buildValidationResponse(errors);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException exception) {
        log.warn(exception.getErrorMessage());
        return ResponseEntity.status(NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponseDto().timestamp(Instant.now()).message(exception.getErrorMessage()));
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class})
    protected ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeException exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponseDto()
                        .timestamp(Instant.now())
                        .message(UNSUPPORTED_MEDIA_TYPE.name()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception, HttpServletRequest request
    ) {
        log.warn("Received {} request for {} endpoint", exception.getMethod(), request.getRequestURI());
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED)
                .body(new ErrorResponseDto()
                        .timestamp(Instant.now())
                        .message(METHOD_NOT_ALLOWED.name()));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);
        var errors = Collections.singletonList(
                new ErrorResponseErrorsInnerDto()
                        .field(exception.getParameterName())
                        .message("Request parameter is mandatory"));
        return buildValidationResponse(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException exception) {

        log.warn(exception.getMessage());
        var errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponseErrorsInnerDto()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage()))
                .toList();
        return buildValidationResponse(errors);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponseDto> handleGeneralExceptions(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto()
                        .timestamp(Instant.now())
                        .message("Internal server error occurred")
                        .details(exception.getMessage()));
    }

    private static ResponseEntity<ErrorResponseDto> buildValidationResponse(List<ErrorResponseErrorsInnerDto> errors) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto()
                        .timestamp(Instant.now())
                        .message(REQUEST_FAILED_VALIDATION)
                        .errors(errors));
    }

}
