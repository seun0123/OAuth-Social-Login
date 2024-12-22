package com.study.oauthsociallogin.common.response;

import org.springframework.http.HttpStatus;

// HTTP 응답을 감싸는 공통 클래스
public class CustomResponseEntity<T> {
    private T data;
    private HttpStatus status;

    private CustomResponseEntity(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    public static <T> CustomResponseEntity<T> success(T data) {
        return new CustomResponseEntity<>(data, HttpStatus.OK);
    }

    public T getData() {
        return data;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
