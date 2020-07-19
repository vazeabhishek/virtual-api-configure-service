package com.invicto.vaconfigureservice.exception.user;

import com.invicto.vaconfigureservice.exception.base.NotExistException;

public class UserNotExistException extends NotExistException {

    private static final String MESSAGE = "User does not exist";

    public UserNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }

}
