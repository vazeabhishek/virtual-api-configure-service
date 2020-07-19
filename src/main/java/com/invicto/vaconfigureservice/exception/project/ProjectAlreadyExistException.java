package com.invicto.vaconfigureservice.exception.project;

import com.invicto.vaconfigureservice.exception.base.AlreadyExistException;

public class ProjectAlreadyExistException extends AlreadyExistException {

    private static final String MESSAGE = "Project already in use, Please use different name";

    public ProjectAlreadyExistException(String identifier) {
        super(MESSAGE, identifier);
    }
}
