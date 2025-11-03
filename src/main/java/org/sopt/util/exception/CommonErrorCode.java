package org.sopt.util.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {

    DATETIME_PARSE_ERROR(HttpStatus.BAD_REQUEST, "DATETIME_PARSE_ERROR", "날짜 형식이 잘못되었습니다. YYYYMMDD 형식으로 입력해주세요."),
    MISMATCHED_INPUT(HttpStatus.BAD_REQUEST, "MISMATCHED_INPUT", "필드 타입이 일치하지 않습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청 형식입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "%s"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다: %s");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String format(Object... args) {
        return String.format(message, args);
    }
}
