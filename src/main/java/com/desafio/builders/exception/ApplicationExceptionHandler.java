package com.desafio.builders.exception;

import com.desafio.builders.exception.entities.StandardError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> notFoundException(HttpServletRequest req, NotFoundException e) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setMessage(e.getMessage());
        error.setPath(req.getRequestURI());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(HttpServletRequest req, EntityNotFoundException e) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setMessage(e.getMessage());
        error.setPath(req.getRequestURI());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardError> NullPointerException(HttpServletRequest req, NullPointerException e) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_GATEWAY.value());
        error.setError("Resource Null Pointer");
        error.setMessage(e.getMessage());
        error.setPath(req.getRequestURI());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(MensagemException.class)
    public ResponseEntity<StandardError> mensagemException(HttpServletRequest req, MensagemException e) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_GATEWAY.value());
        error.setError("Error validating");
        error.setMessage(e.getMessage());
        error.setPath(req.getRequestURI());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest req) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setPath(req.getRequestURI());

        if (e.getCause() instanceof ConstraintViolationException) {
            error.setMessage(e.getMessage());
            error.setStatus(HttpStatus.CONFLICT.value());
            error.setError("Database error");

            return buildResponseEntity(error);
        } else {
            error.setMessage(e.getMessage());
            error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setError("Unexpected error");

            return buildResponseEntity(error);
        }

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<StandardError> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Unexpected error");
        error.setMessage(e.getMessage());
        error.setPath(req.getRequestURI());

        return buildResponseEntity(error);
    }


    private ResponseEntity<StandardError> buildResponseEntity(StandardError error) {
        log.error("Log erro: " + error);
        return ResponseEntity.status(HttpStatus.valueOf(error.getStatus())).body(error);
    }
}
