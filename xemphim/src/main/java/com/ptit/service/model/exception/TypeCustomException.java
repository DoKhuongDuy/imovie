package com.ptit.service.model.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public enum TypeCustomException {
    INTERNAL_SERVER_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, "error"),
    USER_NAME_EXISTED(2, HttpStatus.BAD_REQUEST, "user.name.existed"),
    USER_REGISTER_ERROR(3, HttpStatus.BAD_REQUEST, "user.register.error"),
    USER_NOT_FOUND(4, HttpStatus.BAD_REQUEST, "user.not.found"),
    VIEW_STATUS_NOT_FOUND(5, HttpStatus.BAD_REQUEST, "view.status.not.found"),
    BOOK_MARK_NOT_FOUND(6, HttpStatus.BAD_REQUEST, "book.mark.not.found"),
    BOOK_MARK_EXISTED(7, HttpStatus.BAD_REQUEST, "book.mark.existed"),
    VIEW_HISTORY_NOT_FOUND(8, HttpStatus.BAD_REQUEST, "view.history.not.found"),
    MOVIE_TYPE_NOT_FOUND(9, HttpStatus.BAD_REQUEST, "movie.type.not.found"),
    MOVIE_NOT_FOUND(10, HttpStatus.BAD_REQUEST, "movie.not.found")
    ;


    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    TypeCustomException(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Supplier<CustomException> getDefault() {
        return () -> new CustomException(this);
    }

}
