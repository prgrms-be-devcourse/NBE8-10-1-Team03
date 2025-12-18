package com.nbe8101team03.global.exception.exception;

import com.nbe8101team03.global.exception.errorCode.JwtErrorCode;

public class JwtException extends BaseException {
    public JwtException(JwtErrorCode code) {
        super(code);
    }

    public JwtException(JwtErrorCode code, String logMessage) {
        super(code, logMessage);
    }

    public JwtException(JwtErrorCode code, String logMessage, String clientMessage) {
        super(code, logMessage, clientMessage);
    }
}
