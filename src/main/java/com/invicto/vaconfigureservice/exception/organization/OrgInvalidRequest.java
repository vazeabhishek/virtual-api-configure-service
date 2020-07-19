package com.invicto.vaconfigureservice.exception.organization;

import com.invicto.vaconfigureservice.exception.base.InvalidRequestException;

public class OrgInvalidRequest extends InvalidRequestException {

    public OrgInvalidRequest(String message, String identifier) {
        super(message, identifier);
    }
}
