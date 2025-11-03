package org.sopt.article.entity;

import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.article.exception.ArticleException;

public enum ArticleTag {
    CS, DB, SPRING, ETC;

    public static ArticleTag fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new ArticleException(ArticleErrorCode.ARTICLE_TAG_INVALID);
        }

        String normalized = input.trim().toUpperCase();
        try {
            return ArticleTag.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new ArticleException(ArticleErrorCode.ARTICLE_TAG_INVALID);
        }
    }
}
