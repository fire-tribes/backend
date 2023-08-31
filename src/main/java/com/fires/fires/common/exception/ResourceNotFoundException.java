package com.fires.fires.common.exception;

import com.fires.fires.common.constant.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 애플리케이션 전반 찾고자하는 자원이 존재하지 않을 때 사용하는 exception
 */
@Getter
public class ResourceNotFoundException extends BaseException{
    private final ResponseCode responseCode;

    public ResourceNotFoundException(HttpStatus httpStatus, Throwable e, ResponseCode responseCode) {
        super(httpStatus, e);
        this.responseCode = responseCode;
    }

    /**
     *
     * @param responseCode 자원이 없는 경우 일반적으로는 ResponseCode.RESOURCE_NOT_FOUND을 매개변수로 전달한다.
     */
    public ResourceNotFoundException(ResponseCode responseCode) {
        super(HttpStatus.NOT_FOUND);
        this.responseCode = responseCode;
    }
}
