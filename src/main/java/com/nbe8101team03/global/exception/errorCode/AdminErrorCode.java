package com.nbe8101team03.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminErrorCode implements ErrorCode{
    // 정의 안된 undefiend error
    ADMIN_UNDEFINED_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "ADMIN_UNDEFINED_ERROR"
    ),
    // 생성
    ADMIN_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "ADMIN_ALREADY_EXISTS"
    ),
    // 조회
    ADMIN_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "ADMIN_NOT_FOUND"
    ),
    // 소프트 삭제 admin deactive
    ADMIN_ALREADY_DEACTIVATED(
            HttpStatus.BAD_REQUEST,
            "ADMIN_ALREADY_DEACTIVATED"
    ),
    // 소프트 복구
    ADMIN_ALREADY_ACTIVATED(
            HttpStatus.BAD_REQUEST,
            "ADMIN_ALREADY_ACTIVATED"
    ),
    // 비밀번호 변경
    ADMIN_PASSWORD_MISMATCH(
            HttpStatus.BAD_REQUEST,
            "ADMIN_PASSWORD_MISMATCH"
    ),
    ADMIN_PASSWORD_SAME_AS_OLD(
            HttpStatus.BAD_REQUEST,
            "ADMIN_PASSWORD_SAME_AS_OLD"
    )
    ;

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
