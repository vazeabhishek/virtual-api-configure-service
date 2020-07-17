package com.invicto.vaconfigureservice.exception;

public class OrganizationAlreadyExistsException extends AlreadyExistException {
    private static final String MESSAGE = "Organization already in use, Please use different name";
    public OrganizationAlreadyExistsException(String identifier) {
        super(MESSAGE, identifier);

    }
}
