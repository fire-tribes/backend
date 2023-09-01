package com.fires.fires.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ResponseCode {

    SUCCESS(HttpStatus.OK, 2000, "성공"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 4000, "요청하신 정보가 존재하지 않습니다"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 4001, "회원정보가 존재하지 않습니다"),
    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, 4001, "포트폴리오가 존재하지 않습니다."),
    INVALID_TOKEN_ERROR(HttpStatus.BAD_REQUEST, 4002, "토큰이 유효하지 않습니다."),
    INVALID_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 4002, "입력값이 유효하지 않습니다."),
    NOT_SUPPORTED_METHOD(HttpStatus.BAD_REQUEST, 4002, "지원하지 않는 메소드입니다."),
    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "알 수 없는 서버 에러발생");


    private HttpStatusCode httpStatus;
    private Integer code;
    private String desc;

    ResponseCode(HttpStatus httpStatus, Integer code, String desc) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.desc = desc;
    }
}
