package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.OrderErrorCode;

public class OrderException extends BaseException {
    public OrderException(OrderErrorCode errorCode) {
        super(errorCode);
    }
}
