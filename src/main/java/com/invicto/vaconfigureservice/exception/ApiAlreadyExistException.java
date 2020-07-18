package com.invicto.vaconfigureservice.exception;

public class ApiAlreadyExistException extends AlreadyExistException {

    private static final String MESSAGE = "API already in use, Please use different name";

    public ApiAlreadyExistException(String identifier) {
        super(MESSAGE, identifier);
    }
}
