package com.nbe8101team03.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * error code 에 대한 예시 코드입니다. 해당 error code 를 사용하여 exception 을 정의할 수 있습니다.
 */
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    CREATE_FAIL(HttpStatus.BAD_REQUEST, "입력 형식이 잘못되었습니다."),
    UNKNOWN_PRODUCT(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    MEMBER_WRITE_FAIL(HttpStatus.FORBIDDEN, "상품을 편집할 권한이 없습니다."),
    READ_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "상품 정보를 불러올 수 없습니다."),

    UNKNOWN_IMAGE(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다.");


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
