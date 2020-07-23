package com.invicto.vaconfigureservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.exception.base.*;
import com.invicto.vaconfigureservice.response.ErrorResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class Handler extends ResponseEntityExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<Object> handleNotExistException(NotExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<Object> handleNoPermissionException(NoPermissionException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        log.info(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JsonConversionFailureException.class)
    public ResponseEntity<Object> handleJsonConversionFailureException(JsonConversionFailureException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnAnticipatedException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDetail(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorResponse.toJsonString(objectMapper), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
