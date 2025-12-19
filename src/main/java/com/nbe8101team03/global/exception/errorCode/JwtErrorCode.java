package com.nbe8101team03.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCode {
//    "authorization header or bearer token does not exist"
    JWT_MISSING(HttpStatus.UNAUTHORIZED, "JWT_MISSING"),
//    "jwt token is invalid"
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT_INVALID"),
//    "jwt token has expired"
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_EXPIRED"),
//    "do not have access permission"
    JWT_FORBIDDEN(HttpStatus.FORBIDDEN, "JWT_FORBIDDEN");

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
