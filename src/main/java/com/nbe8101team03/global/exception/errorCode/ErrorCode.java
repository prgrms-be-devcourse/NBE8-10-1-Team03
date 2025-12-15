package com.nbe8101team03.global.exception.errorCode;

import org.springframework.http.HttpStatus;

/**
 * 에러 코드에 대한 정의(인터페이스)입니다. 모든 custom exception 은 해당 예외를 정의하기 위해
 * ErrorCode 를 상속받는 구현 enum 클래스를 만들어야 합니다.
 */
public interface ErrorCode {

    HttpStatus getHttpStatus();

    String getMessage();

}
