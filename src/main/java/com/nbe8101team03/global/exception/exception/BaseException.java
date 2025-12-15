package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.ErrorCode;
import lombok.Getter;

/**
 * 모든 custom 예외의 조상 예외 객체입니다. RuntimeException 을 직접적으로 상속받아 처리합니다.
 * ErrorCode 와, 콘솔에 띄울 로그 메시지를 정의할 수 있습니다.
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String logMessage;

    /**
     * ErrorCode 만 받아 처리합니다. log message 가 누락되어 있습니다.
     * @param errorCode 정의된 에러 코드
     */
    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage(), null, false, false);
        this.errorCode = errorCode;
        this.logMessage = "[client] " + errorCode.getMessage();
    }

    /**
     * ErrorCode 및 로그 메시지를 정의합니다.
     * @param errorCode 정의된 에러 코드
     * @param logMessage 콘솔에 띄울 메시지
     */
    protected BaseException(ErrorCode errorCode, String logMessage) {
        super(errorCode.getMessage(), null, false, false);
        this.errorCode = errorCode;
        this.logMessage = "[log] " + logMessage;
    }

    /**
     * ErrorCode 및 로그 메시지를 정의합니다.
     * @param errorCode 정의된 에러 코드
     * @param logMessage 콘솔에 띄울 메시지
     * @param clientMessage 클라이언트에게 전송될 재정의된 메시지
     */
    protected BaseException(ErrorCode errorCode, String logMessage, String clientMessage) {
        super(clientMessage, null, false, false);
        this.errorCode = errorCode;
        this.logMessage = "[log] " + logMessage;
    }
}
