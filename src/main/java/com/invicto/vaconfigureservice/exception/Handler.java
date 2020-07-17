package com.invicto.vaconfigureservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.response.ErrorResponse;
import com.invicto.vaconfigureservice.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<Object> handleNotExistException(NotExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<Object> handleNoPermissionException(NoPermissionException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JsonConversionFailureException.class)
    public ResponseEntity<Object> handleJsonConversionFailureException(JsonConversionFailureException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnAnticipatedException(JsonConversionFailureException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
