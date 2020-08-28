package com.invicto.vaconfigureservice.exception.project;


import com.invicto.vaconfigureservice.exception.base.NotExistException;

public class CollectionNotExistException extends NotExistException {

    private static final String MESSAGE = "Collection does not exist";

    public CollectionNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }

}
