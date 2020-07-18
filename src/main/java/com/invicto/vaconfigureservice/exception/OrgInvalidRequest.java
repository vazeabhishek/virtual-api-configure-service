package com.invicto.vaconfigureservice.exception;

public class OrgInvalidRequest extends InvalidRequestException {

    public OrgInvalidRequest(String message, String identifier) {
        super(message, identifier);
    }
}
