package org.sopt.article.exception;

import org.sopt.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ArticleErrorCode implements ErrorCode {

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE_NOT_FOUND", "해당 ID의 게시글을 찾을 수 없습니다."),
    ARTICLE_DUPLICATE_TITLE(HttpStatus.CONFLICT, "ARTICLE_DUPLICATE_TITLE", "이미 존재하는 제목입니다: %s"),
    ARTICLE_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE_MEMBER_NOT_FOUND", "게시글 작성자를 찾을 수 없습니다."),
    ARTICLE_UNAUTHORIZED(HttpStatus.FORBIDDEN, "ARTICLE_UNAUTHORIZED", "게시글을 수정/삭제할 권한이 없습니다."),
    ARTICLE_TAG_INVALID(HttpStatus.BAD_REQUEST, "ARTICLE_TAG_INVALID", "유효하지 않은 태그입니다. 사용 가능한 태그: CS, DB, SPRING, ETC"),

    ARTICLE_MEMBER_REQUIRED(HttpStatus.BAD_REQUEST, "ARTICLE_MEMBER_REQUIRED", "작성자는 필수 입력 항목입니다."),
    ARTICLE_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "ARTICLE_TITLE_REQUIRED", "제목은 필수 입력 항목입니다."),
    ARTICLE_TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "ARTICLE_TITLE_TOO_LONG", "제목은 최대 100자까지 입력 가능합니다."),
    ARTICLE_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "ARTICLE_CONTENT_REQUIRED", "내용은 필수 입력 항목입니다."),
    ARTICLE_CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "ARTICLE_CONTENT_TOO_LONG", "내용은 최대 5000자까지 입력 가능합니다."),
    ARTICLE_TAG_REQUIRED(HttpStatus.BAD_REQUEST, "ARTICLE_TAG_REQUIRED", "태그는 필수 입력 항목입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ArticleErrorCode(HttpStatus httpStatus, String code, String message) {
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