package com.invicto.vaconfigureservice.exception;

public class UserNotExistException extends NotExistException {

    private static final String MESSAGE = "User does not exist";

    public UserNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }

}
