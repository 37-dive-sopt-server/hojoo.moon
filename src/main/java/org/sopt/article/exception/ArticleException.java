package org.sopt.article.exception;

import org.sopt.util.exception.ErrorCode;
import org.sopt.util.exception.GeneralException;

public class ArticleException extends GeneralException {
    public ArticleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
