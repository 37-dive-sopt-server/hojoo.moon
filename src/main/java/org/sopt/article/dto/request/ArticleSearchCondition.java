package org.sopt.article.dto.request;

public record ArticleSearchCondition(
        String title,
        String authorName
) {
}