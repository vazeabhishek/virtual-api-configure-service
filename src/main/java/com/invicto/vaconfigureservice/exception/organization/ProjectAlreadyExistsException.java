package com.invicto.vaconfigureservice.exception.organization;

import com.invicto.vaconfigureservice.exception.base.AlreadyExistException;

public class ProjectAlreadyExistsException extends AlreadyExistException {
    private static final String MESSAGE = "Project already in use, Please use different name";
    public ProjectAlreadyExistsException(String identifier) {
        super(MESSAGE, identifier);

    }
}
