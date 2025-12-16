package com.nbe8101team03.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminErrorCode implements ErrorCode{
    //아직 정의 안 됨 리팩토링 필요
    ADMIN_UNDEFINED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ADMIN_UNDEFINED_ERROR");

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
