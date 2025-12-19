package com.nbe8101team03.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "유저 정보를 불러올 수 없습니다."),
    ALREADY_USED_USER_EMAIL(HttpStatus.CONFLICT,"이미 사용중인 이메일입니다."),
    NOT_DELETE_USER(HttpStatus.FORBIDDEN, "이미 주문이 들어간 유저입니다.");


    private final HttpStatus status;
    private final String message;


    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

