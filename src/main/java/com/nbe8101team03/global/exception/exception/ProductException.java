package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.ProductErrorCode;

/**
 * 상품 관리 시 발생하는 예외를 정의하였습니다. 기존에 정의한 예외 코드를 사용하여, 예외를 구조화하고, 추후 exception handler
 * 를 통해, 클라이언트로의 예외 메시지 구조를 정립하였습니다.
 *
 * @see BaseException
 * @see ProductErrorCode
 */
public class ProductException extends BaseException {

    public ProductException(ProductErrorCode code) {
         super(code);
    }

    public ProductException(ProductErrorCode code, String logMessage) {
        super(code, logMessage);
    }

    public ProductException(ProductErrorCode code, String logMessage, String clientMessage) {
        super(code, logMessage, clientMessage);
    }

}
