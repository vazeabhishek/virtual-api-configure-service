package com.invicto.vaconfigureservice.exception.api;

import com.invicto.vaconfigureservice.exception.base.InvalidRequestException;

public class ApiInvalidRequest extends InvalidRequestException {

    public ApiInvalidRequest(String message,String identifier) {
        super(message, identifier);
    }
}
