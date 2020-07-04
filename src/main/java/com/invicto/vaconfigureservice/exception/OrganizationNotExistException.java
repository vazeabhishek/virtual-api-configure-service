package com.invicto.vaconfigureservice.exception;

public class OrganizationNotExistException extends NotExistException {

    private static final String MESSAGE = "Organization does not exist";

    public OrganizationNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }
}
