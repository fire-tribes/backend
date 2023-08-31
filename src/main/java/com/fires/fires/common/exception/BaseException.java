package com.fires.fires.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException{
    private final HttpStatus httpStatus;

    public BaseException(HttpStatus httpStatus, Throwable e) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public BaseException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
