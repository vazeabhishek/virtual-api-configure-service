package com.invicto.vaconfigureservice.exception;


public class ProjectNotExistException extends NotExistException {

    private static final String MESSAGE = "Project does not exist";

    public ProjectNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }

}
