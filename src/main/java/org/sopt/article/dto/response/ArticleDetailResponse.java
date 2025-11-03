package org.sopt.article.dto.response;

import org.sopt.article.entity.Article;
import org.sopt.article.entity.ArticleTag;

import java.time.LocalDateTime;

public record ArticleDetailResponse(
        Long id,
        String title,
        String content,
        ArticleTag tag,
        LocalDateTime createdAt,
        String author
) {
    public static ArticleDetailResponse from(Article article) {
        return new ArticleDetailResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag(),
                article.getCreatedAt(),
                article.getMember().getName()
        );
    }
}