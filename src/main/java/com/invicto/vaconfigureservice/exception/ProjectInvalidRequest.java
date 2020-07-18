package com.invicto.vaconfigureservice.exception;

public class ProjectInvalidRequest extends InvalidRequestException {

    public ProjectInvalidRequest(String message, String identifier) {
        super(message, identifier);
    }
}
