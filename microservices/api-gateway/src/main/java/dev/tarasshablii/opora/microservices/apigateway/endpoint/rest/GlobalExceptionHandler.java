package dev.tarasshablii.opora.microservices.apigateway.endpoint.rest;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.ErrorResponseDto;
import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.dto.ErrorResponseErrorsInnerDto;
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
import org.springframework.web.client.UnknownContentTypeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_REQUEST_BODY = "Invalid request body";
    private static final String REQUEST_FAILED_VALIDATION = "Request failed validation";

    @ExceptionHandler({WebClientResponseException.NotFound.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(WebClientResponseException.NotFound exception) {
        log.error(exception.getStatusText(), exception);
        return ResponseEntity.status(NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getResponseBodyAs(ErrorResponseDto.class));
    }

    @ExceptionHandler({WebClientRequestException.class})
    public ResponseEntity<ErrorResponseDto> handleNotAvailable(WebClientRequestException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponseDto.builder()
                        .timestamp(Instant.now())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadable(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest()
                .body(ErrorResponseDto.builder()
                        .timestamp(Instant.now())
                        .message(INVALID_REQUEST_BODY)
                        .details(exception.getMessage())
                        .build());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        var errors = Collections.singletonList(
                ErrorResponseErrorsInnerDto.builder()
                        .field(exception.getPropertyName())
                        .message(exception.getMessage())
                        .build());
        ErrorResponseDto responseDto = ErrorResponseDto.builder()
                .timestamp(Instant.now())
                .message("Type mismatch occurred, see errors for details")
                .errors(errors)
                .build();
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {

        log.debug(exception.getMessage(), exception);
        var errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> ErrorResponseErrorsInnerDto
                        .builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();
        return buildValidationResponse(errors);
    }


    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
            UnknownContentTypeException.class})
    protected ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeException exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity
                .badRequest()
                .body(ErrorResponseDto.builder()
                        .timestamp(Instant.now())
                        .message(UNSUPPORTED_MEDIA_TYPE.name())
                        .build());
    }

    @ExceptionHandler(WebClientResponseException.BadRequest.class)
    protected ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotSupportedException(
            WebClientResponseException.BadRequest exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity
                .badRequest()
                .body(exception.getResponseBodyAs(ErrorResponseDto.class));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception, HttpServletRequest request
    ) {
        log.warn("Received {} request for {} endpoint", exception.getMethod(), request.getRequestURI());
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED)
                .body(ErrorResponseDto.builder()
                        .timestamp(Instant.now())
                        .message(METHOD_NOT_ALLOWED.name())
                        .build());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);
        var errors = Collections.singletonList(
                ErrorResponseErrorsInnerDto.builder()
                        .field(exception.getParameterName())
                        .message("Request parameter is mandatory")
                        .build());
        return buildValidationResponse(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException exception) {

        log.warn(exception.getMessage());
        var errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponseErrorsInnerDto
                        .builder()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build())
                .toList();
        return buildValidationResponse(errors);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponseDto> handleGeneralExceptions(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDto.builder()
                        .timestamp(Instant.now())
                        .message("Internal server error occurred")
                        .details(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralExceptions(WebClientResponseException.InternalServerError exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(exception.getResponseBodyAs(ErrorResponseDto.class));
    }

    private static ResponseEntity<ErrorResponseDto> buildValidationResponse(List<ErrorResponseErrorsInnerDto> errors) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseDto.builder()
                        .timestamp(Instant.now())
                        .message(REQUEST_FAILED_VALIDATION)
                        .errors(errors)
                        .build());
    }

}
