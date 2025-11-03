package org.sopt.util.exception;

import org.springframework.http.HttpStatus;

public enum StorageErrorCode implements ErrorCode {

    STORAGE_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_LOAD_FAILED", "회원 데이터 파일 로드 실패"),
    STORAGE_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "STORAGE_INVALID_FORMAT", "회원 데이터 파일 형식이 올바르지 않습니다."),
    STORAGE_PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_PARSE_FAILED", "회원 데이터 파일 파싱 실패."),
    STORAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_SAVE_FAILED", "회원 데이터 파일 저장 실패");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    StorageErrorCode(HttpStatus httpStatus, String code, String message) {
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
