package com.nbe8101team03.global.exception.handler;


import com.nbe8101team03.global.exception.exception.BaseException;
import com.nbe8101team03.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 헨들러입니다. 코드에서 발생하는 예외가 클라이언트로 넘어가기 전, 예외를 잡아 적절한 형식을 가공합니다. <br>
 * log message 표시 여부는 해당 클래스에서 지정합니다.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private ResponseEntity<Object> fail(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(CommonResponse.fail(message));
    }

    //todo: 추후 환경 변수 주입으로 변경
    private boolean isDebug = true;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException exception) {

        if(isDebug) {
            log.warn("{}", exception.getLogMessage());
        }

        return fail(exception.getMessage(), exception.getErrorCode().getHttpStatus());
    }

    /**
     * 예상치 못한 예외를 정의합니다. 위 `handleBaseException` 의 경우, 사전에 정의된 예외를 잡아 처리하지만, 해당 메서드의 경우
     * 정의되지 못한 예외가 발생하는 경우, 해당 예외를 그대로 클라이언트로 보내는 것이 아닌, 좀 더 "읽기 좋은" 형태로 가공합니다.
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        log.error("[unexpected] {}", ex.getMessage(), ex);
        return fail("unknown internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
