package com.whoami.milkcheque.validation;

import com.whoami.milkcheque.exception.*;
import com.whoami.milkcheque.dto.SignUpResponse;
import com.whoami.milkcheque.dto.LoginResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationFormatException.class)
    public ResponseEntity<Object> handleAuthenticationFormatException(
            AuthenticationFormatException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SignUpProcessFailureException.class)
    public ResponseEntity<Object> handleSigupProcessFailureException(
            SignUpProcessFailureException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new SignUpResponse(exception.getCode(),
                                     exception.getMessage()));
    }

    @ExceptionHandler(LoginProcessFailureException.class)
    public ResponseEntity<Object> handleLoginProcessFailureException(
            LoginProcessFailureException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new LoginResponse(exception.getCode(),
                                    exception.getMessage(),
                                    ""));
    }
}
