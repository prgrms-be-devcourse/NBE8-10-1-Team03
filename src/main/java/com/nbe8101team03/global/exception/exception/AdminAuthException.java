package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.AdminAuthErrorCode;

public class AdminAuthException extends BaseException {
    public AdminAuthException(AdminAuthErrorCode code) {
        super(code);
    }

    public AdminAuthException(AdminAuthErrorCode code, String logMessage) {
        super(code, logMessage);
    }

    public AdminAuthException(AdminAuthErrorCode code, String logMessage, String clientMessage) {
        super(code, logMessage, clientMessage);
    }
}
