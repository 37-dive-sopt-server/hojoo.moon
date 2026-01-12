package org.sopt.comment.exception;

import org.sopt.util.exception.ErrorCode;
import org.sopt.util.exception.GeneralException;

public class CommentException extends GeneralException {
    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CommentException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public CommentException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
