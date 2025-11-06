package org.sopt.article.exception;

import org.sopt.util.exception.ErrorCode;
import org.sopt.util.exception.GeneralException;

public class ArticleException extends GeneralException {
    public ArticleException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ArticleException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public ArticleException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
