package com.nbe8101team03.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminAuthErrorCode implements ErrorCode{

    //            "ADMIN_LOGIN_FAILED",
    ADMIN_LOGIN_FAILED(
            HttpStatus.UNAUTHORIZED,
            "id or password is incorrect"
    ),
    //            "ADMIN_INACTIVE",
    ADMIN_INACTIVE(
            HttpStatus.FORBIDDEN,
            "admin is already inactive"
    );

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
