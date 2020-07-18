package com.invicto.vaconfigureservice.exception;

public class ApiInvalidRequest extends InvalidRequestException {

    public ApiInvalidRequest(String message,String identifier) {
        super(message, identifier);
    }
}
