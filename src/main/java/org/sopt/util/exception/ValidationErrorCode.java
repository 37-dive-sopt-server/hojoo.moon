package org.sopt.util.exception;

import org.springframework.http.HttpStatus;

public enum ValidationErrorCode implements ErrorCode {

    MEMBER_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "MEMBER_NAME_REQUIRED", "이름은 필수 입력 항목입니다."),
    MEMBER_NAME_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER_NAME_INVALID_FORMAT", "이름은 한글만 입력 가능합니다."),
    MEMBER_NAME_INVALID_LENGTH(HttpStatus.BAD_REQUEST, "MEMBER_NAME_INVALID_LENGTH", "이름은 최소 2자 이상, 최대 50자까지 입력 가능합니다."),

    MEMBER_EMAIL_REQUIRED(HttpStatus.BAD_REQUEST, "MEMBER_EMAIL_REQUIRED", "이메일은 필수 입력 항목입니다."),
    MEMBER_EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER_EMAIL_INVALID_FORMAT", "유효하지 않은 이메일 형식입니다."),

    MEMBER_BIRTH_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "MEMBER_BIRTH_DATE_REQUIRED", "생년월일은 필수 입력 항목입니다."),
    MEMBER_BIRTH_DATE_FUTURE(HttpStatus.BAD_REQUEST, "MEMBER_BIRTH_DATE_FUTURE", "생년월일은 현재 날짜보다 이전이어야 합니다."),

    MEMBER_GENDER_REQUIRED(HttpStatus.BAD_REQUEST, "MEMBER_GENDER_REQUIRED", "성별은 필수 입력 항목입니다."),
    MEMBER_GENDER_INVALID(HttpStatus.BAD_REQUEST, "MEMBER_GENDER_INVALID", "유효하지 않은 성별 입력입니다: %s");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ValidationErrorCode(HttpStatus httpStatus, String code, String message) {
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
