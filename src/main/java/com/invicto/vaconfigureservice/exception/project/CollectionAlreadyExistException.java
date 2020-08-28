package com.invicto.vaconfigureservice.exception.project;

import com.invicto.vaconfigureservice.exception.base.AlreadyExistException;

public class CollectionAlreadyExistException extends AlreadyExistException {

    private static final String MESSAGE = "Collection already in use, Please use different name";

    public CollectionAlreadyExistException(String identifier) {
        super(MESSAGE, identifier);
    }
}
