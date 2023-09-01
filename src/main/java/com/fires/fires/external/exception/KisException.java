package com.fires.fires.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 한국 투자증권 api호출 중 발생한 exception처리용
 */
public class KisException extends BaseException{
    public KisException(HttpStatus httpStatus, Throwable e) {
        super(httpStatus, e);
    }
}
