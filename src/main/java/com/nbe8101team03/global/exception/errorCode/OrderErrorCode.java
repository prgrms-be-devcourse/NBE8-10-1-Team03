package com.nbe8101team03.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public enum OrderErrorCode implements ErrorCode{
    UNKNOWN_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    UNKNOWN_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 입니다."),
    ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다."),
    COMPLETED_ORDER_CANNOT_DELETE(HttpStatus.BAD_REQUEST, "완료된 주문은 삭제할 수 없습니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "주문 수량은 1개 이상이어야 합니다.");


    private final HttpStatus httpStatus;
    private final String message;

    OrderErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
