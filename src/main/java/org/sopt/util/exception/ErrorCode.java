package org.sopt.util.exception;

public enum ErrorCode {

    NAME_REQUIRED("이름은 필수 입력 항목입니다."),
    NAME_KOREAN_ONLY("이름은 한글만 입력 가능합니다."),
    NAME_MIN_LENGTH("이름은 최소 2자 이상이어야 합니다."),
    NAME_MAX_LENGTH("이름은 최대 50자까지 입력 가능합니다."),
    NAME_NO_SPACE("이름에 공백은 사용할 수 없습니다."),

    EMAIL_REQUIRED("이메일은 필수 입력 항목입니다."),
    EMAIL_INVALID_FORMAT("유효하지 않은 이메일 형식입니다."),

    BIRTH_DATE_REQUIRED("생년월일은 필수 입력 항목입니다."),
    BIRTH_DATE_FUTURE("생년월일은 현재 날짜보다 이전이어야 합니다."),

    GENDER_REQUIRED("성별은 필수 입력 항목입니다."),
    GENDER_INVALID("유효하지 않은 성별 입력입니다: %s"),

    MINIMUM_AGE("만 %d세 미만은 가입할 수 없습니다."),

    DUPLICATE_EMAIL("이미 존재하는 이메일입니다: %s"),
    MEMBER_NOT_FOUND("해당 ID의 회원을 찾을 수 없습니다: %d"),
    ID_ALREADY_EXISTS("이미 ID가 설정된 회원입니다."),

    STORAGE_LOAD_FAILED("회원 데이터 파일 로드 실패"),
    STORAGE_INVALID_FORMAT("회원 데이터 파일 형식이 올바르지 않습니다."),
    STORAGE_PARSE_FAILED("회원 데이터 파일 파싱 실패."),
    STORAGE_SAVE_FAILED("회원 데이터 파일 저장 실패");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}