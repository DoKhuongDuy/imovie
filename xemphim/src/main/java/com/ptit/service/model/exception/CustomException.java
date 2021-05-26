package com.ptit.service.model.exception;

public class CustomException extends RuntimeException {

    private final TypeCustomException typeCustomException;

    public CustomException(TypeCustomException typeCustomException) {
        super(typeCustomException.getMessage());
        this.typeCustomException = typeCustomException;
    }

    public CustomException() {
        typeCustomException = TypeCustomException.INTERNAL_SERVER_ERROR;
    }


    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.typeCustomException = TypeCustomException.INTERNAL_SERVER_ERROR;
    }

    public CustomException(Throwable cause) {
        super(cause);
        this.typeCustomException = TypeCustomException.INTERNAL_SERVER_ERROR;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.typeCustomException = TypeCustomException.INTERNAL_SERVER_ERROR;
    }

    public TypeCustomException getTypeCustomException() {
        return typeCustomException;
    }
}
