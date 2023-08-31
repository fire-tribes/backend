package com.fires.fires.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 응답 표준화를 위한 응답 객체
 * @param <U> 메타정보 타입 지정
 * @param <T> 데이터 타입 지정
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Response <U, T>{

    //자체 정의 코드 정보 포함
    private Integer code;
    //요청과 관련된 메다 정보 저장 필드
    private U meta;
    //요청 응답이 포함된 필드
    private T data;

    //메타정보가 불필요한 경우 활용
    public static <T> Response<Void, T> success(Integer code, T data) {
        return new Response<>(code, null, data);
    }

    public static <U, T> Response<U, T> successWithMeta(Integer code, U meta, T data) {
        return new Response<>(code, meta, data);
    }

    public static <U> Response<U, Void> failWithMeta(Integer code, U meta) {
        return new Response<>(code, meta,null);
    }
}
