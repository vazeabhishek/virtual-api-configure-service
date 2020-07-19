package com.invicto.vaconfigureservice.exception.project;

import com.invicto.vaconfigureservice.exception.base.InvalidRequestException;

public class ProjectInvalidRequest extends InvalidRequestException {

    public ProjectInvalidRequest(String message, String identifier) {
        super(message, identifier);
    }
}
