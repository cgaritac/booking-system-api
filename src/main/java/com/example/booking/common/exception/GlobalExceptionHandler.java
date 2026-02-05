package com.example.booking.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
            .badRequest()
            .body(errorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
            ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(errorResponse(HttpStatus.UNAUTHORIZED, "Email o contraseña incorrectos"));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabled(DisabledException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(errorResponse(HttpStatus.UNAUTHORIZED, "Cuenta deshabilitada"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthentication(AuthenticationException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(errorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    private Map<String, Object> errorResponse(HttpStatus status, String message) {
        return Map.of(
            "timestamp", Instant.now(),
            "status", status.value(),
            "error", status.getReasonPhrase(),
            "message", message
        );
    }
}
