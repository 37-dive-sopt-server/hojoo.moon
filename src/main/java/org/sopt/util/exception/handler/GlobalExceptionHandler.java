package org.sopt.util.exception.handler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.sopt.util.exception.ErrorCode;
import org.sopt.util.exception.GeneralException;
import org.sopt.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseResponse<?>> handleGeneralException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.onFailure(errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable rootCause = getRootCause(e);

        String code;
        String message;

        if (rootCause instanceof DateTimeParseException) {
            code = "DATETIME_PARSE_ERROR";
            message = "날짜 형식이 잘못되었습니다. YYYYMMDD 형식으로 입력해주세요.";
        } else if (rootCause instanceof MismatchedInputException) {
            code = "MISMATCHED_INPUT";
            message = "필드 타입이 일치하지 않습니다.";
        } else {
            code = "BAD_REQUEST";
            message = "잘못된 요청 형식입니다.";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.onFailure(code, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.onFailure(
                        "INTERNAL_SERVER_ERROR",
                        "서버 내부 오류가 발생했습니다: " + e.getMessage()
                ));
    }

    private Throwable getRootCause(Throwable ex) {
        Throwable cur = ex;
        while (cur.getCause() != null) {
            cur = cur.getCause();
        }
        return cur;
    }
}
