package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.UserErrorCode;

public class UserException extends BaseException {

    public UserException(UserErrorCode code) {
        super(code);
    }

    public UserException(UserErrorCode code, String logMessage) {
        super(code, logMessage);
    }

    public UserException(UserErrorCode code, String logMessage, String clientMessage) {
        super(code, logMessage, clientMessage);
    }

}