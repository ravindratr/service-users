package org.service.users.exception;

public class ValidationException extends RuntimeException{

    private String code;

    public ValidationException(String code) {
        super(code);
        this.code = code;
    }
}
