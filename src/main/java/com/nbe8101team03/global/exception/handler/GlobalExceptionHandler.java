package com.nbe8101team03.global.exception.handler;


import com.nbe8101team03.global.exception.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    //todo: 추후 환경 변수 주입으로 변경
    private boolean isDebug = true;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException exception) {

        if(isDebug) {
            log.warn("{}", exception.getLogMessage());
        }

        //todo: 추후 응답 형식 지정 이후 적절한 값으로 변경
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(exception.getErrorCode());

    }


}
