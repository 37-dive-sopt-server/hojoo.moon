package org.sopt.comment.exception;

import org.sopt.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements ErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "해당 ID의 댓글을 찾을 수 없습니다."),
    COMMENT_ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_ARTICLE_NOT_FOUND", "댓글을 작성할 게시글을 찾을 수 없습니다."),
    COMMENT_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_MEMBER_NOT_FOUND", "댓글 작성자를 찾을 수 없습니다."),
    COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN, "COMMENT_UNAUTHORIZED", "댓글을 수정/삭제할 권한이 없습니다."),
    COMMENT_ARTICLE_MISMATCH(HttpStatus.BAD_REQUEST, "COMMENT_ARTICLE_MISMATCH", "해당 게시글의 댓글이 아닙니다."),

    COMMENT_ARTICLE_REQUIRED(HttpStatus.BAD_REQUEST, "COMMENT_ARTICLE_REQUIRED", "게시글은 필수 입력 항목입니다."),
    COMMENT_MEMBER_REQUIRED(HttpStatus.BAD_REQUEST, "COMMENT_MEMBER_REQUIRED", "작성자는 필수 입력 항목입니다."),
    COMMENT_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "COMMENT_CONTENT_REQUIRED", "댓글 내용은 필수 입력 항목입니다."),
    COMMENT_CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "COMMENT_CONTENT_TOO_LONG", "댓글은 최대 1000자까지 입력 가능합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommentErrorCode(HttpStatus httpStatus, String code, String message) {
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
