package org.sopt.article.dto.response;

import org.sopt.article.entity.Article;

import java.time.LocalDateTime;

public record ArticleListResponse(
        String title,
        LocalDateTime createdAt,
        String author
) {
    public static ArticleListResponse from(Article article) {
        return new ArticleListResponse(
                article.getTitle(),
                article.getCreatedAt(),
                article.getMember().getName()
        );
    }
}