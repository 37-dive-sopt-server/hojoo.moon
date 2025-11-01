package org.sopt.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NAME_REQUIRED(HttpStatus.BAD_REQUEST, "VALIDATION_001", "이름은 필수 입력 항목입니다."),
    NAME_KOREAN_ONLY(HttpStatus.BAD_REQUEST, "VALIDATION_002", "이름은 한글만 입력 가능합니다."),
    NAME_MIN_LENGTH(HttpStatus.BAD_REQUEST, "VALIDATION_003", "이름은 최소 2자 이상이어야 합니다."),
    NAME_MAX_LENGTH(HttpStatus.BAD_REQUEST, "VALIDATION_004", "이름은 최대 50자까지 입력 가능합니다."),
    NAME_NO_SPACE(HttpStatus.BAD_REQUEST, "VALIDATION_005", "이름에 공백은 사용할 수 없습니다."),

    EMAIL_REQUIRED(HttpStatus.BAD_REQUEST, "VALIDATION_006", "이메일은 필수 입력 항목입니다."),
    EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "VALIDATION_007", "유효하지 않은 이메일 형식입니다."),

    BIRTH_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "VALIDATION_008", "생년월일은 필수 입력 항목입니다."),
    BIRTH_DATE_FUTURE(HttpStatus.BAD_REQUEST, "VALIDATION_009", "생년월일은 현재 날짜보다 이전이어야 합니다."),

    GENDER_REQUIRED(HttpStatus.BAD_REQUEST, "VALIDATION_010", "성별은 필수 입력 항목입니다."),
    GENDER_INVALID(HttpStatus.BAD_REQUEST, "VALIDATION_011", "유효하지 않은 성별 입력입니다: %s"),

    MINIMUM_AGE(HttpStatus.BAD_REQUEST, "VALIDATION_012", "만 %d세 미만은 가입할 수 없습니다."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "BUSINESS_001", "이미 존재하는 이메일입니다: %s"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "BUSINESS_002", "해당 ID의 회원을 찾을 수 없습니다: %d"),
    ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "BUSINESS_003", "이미 ID가 설정된 회원입니다."),

    STORAGE_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_001", "회원 데이터 파일 로드 실패"),
    STORAGE_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "STORAGE_002", "회원 데이터 파일 형식이 올바르지 않습니다."),
    STORAGE_PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_003", "회원 데이터 파일 파싱 실패."),
    STORAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_004", "회원 데이터 파일 저장 실패");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}