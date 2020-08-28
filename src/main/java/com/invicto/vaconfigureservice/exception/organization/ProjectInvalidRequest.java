package com.invicto.vaconfigureservice.exception.organization;

import com.invicto.vaconfigureservice.exception.base.InvalidRequestException;

public class ProjectInvalidRequest extends InvalidRequestException {

    public ProjectInvalidRequest(String message, String identifier) {
        super(message, identifier);
    }
}
