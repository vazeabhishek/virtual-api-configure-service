package com.invicto.vaconfigureservice.exception;

public class AlreadyExistException extends RuntimeException {
    private String message;
    private String identifier;

    public AlreadyExistException(String message, String identifier) {
        this.identifier = identifier;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + ", Identifier -> " + identifier + "";
    }
}
