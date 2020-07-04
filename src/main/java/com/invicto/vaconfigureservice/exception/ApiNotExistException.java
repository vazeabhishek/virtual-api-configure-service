package com.invicto.vaconfigureservice.exception;

public class ApiNotExistException extends NotExistException {

    private static final String MESSAGE = "Api does not exist";

    public ApiNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }

}
