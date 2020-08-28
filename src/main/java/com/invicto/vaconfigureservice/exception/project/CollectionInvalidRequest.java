package com.invicto.vaconfigureservice.exception.project;

import com.invicto.vaconfigureservice.exception.base.InvalidRequestException;

public class CollectionInvalidRequest extends InvalidRequestException {

    public CollectionInvalidRequest(String message, String identifier) {
        super(message, identifier);
    }
}
