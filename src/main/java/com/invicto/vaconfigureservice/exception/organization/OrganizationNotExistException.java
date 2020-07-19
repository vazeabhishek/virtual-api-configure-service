package com.invicto.vaconfigureservice.exception.organization;

import com.invicto.vaconfigureservice.exception.base.NotExistException;

public class OrganizationNotExistException extends NotExistException {

    private static final String MESSAGE = "Organization does not exist";

    public OrganizationNotExistException(String identifier) {
        super(MESSAGE, identifier);

    }
}
