package com.invicto.vaconfigureservice.exception.api;

import com.invicto.vaconfigureservice.exception.base.NotExistException;

public class ApiNotExistException extends NotExistException {

    private static final String MESSAGE = "Api does not exist";

    public ApiNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }

}
