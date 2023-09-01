package com.fires.fires.common.exception;

import com.fires.fires.common.constant.ResponseCode;
import com.fires.fires.common.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String, Void>> UnknownExceptionHandler(Exception e) {
        log.error("[Application Error] Unknown Error", e);
        Response<String, Void> errorResponse = Response.failWithMeta(ResponseCode.UNKNOWN_SERVER_ERROR.getCode(), ResponseCode.UNKNOWN_SERVER_ERROR.getDesc());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response<String, Void>> httpMethodExHandler(HttpRequestMethodNotSupportedException e) {
        log.error("[Application Error] Not Supported Method", e);

        Response<String, Void> response = Response.failWithMeta(ResponseCode.NOT_SUPPORTED_METHOD.getCode(), ResponseCode.NOT_SUPPORTED_METHOD.getDesc());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
