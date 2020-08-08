package com.invicto.vaconfigureservice.exception.organization;

import com.invicto.vaconfigureservice.exception.base.NotExistException;

public class ProjectNotExistException extends NotExistException {

    private static final String MESSAGE = "Project does not exist";

    public ProjectNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }
}
