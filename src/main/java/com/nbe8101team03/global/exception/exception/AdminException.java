package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.AdminErrorCode;

public class AdminException extends BaseException {
    public AdminException(AdminErrorCode code) {
        super(code);
    }

    public AdminException(AdminErrorCode code, String logMessage) {
        super(code, logMessage);
    }

    public AdminException(AdminErrorCode code, String logMessage, String clientMessage) {
        super(code, logMessage, clientMessage);
    }
}
