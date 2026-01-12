package org.sopt.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.article.entity.Article;
import org.sopt.comment.exception.CommentException;
import org.sopt.member.entity.Member;
import org.sopt.util.entity.BaseEntity;

import java.util.Objects;

import static org.sopt.comment.exception.CommentErrorCode.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Comment extends BaseEntity {

    private static final int MAX_CONTENT_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = MAX_CONTENT_LENGTH)
    private String content;

    public static Comment create(Article article, Member member, String content) {
        validateArticle(article);
        validateMember(member);
        validateContent(content);

        return Comment.builder()
                .article(article)
                .member(member)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public void validateOwnership(Long memberId) {
        if (!this.member.getId().equals(memberId)) {
            throw new CommentException(COMMENT_UNAUTHORIZED);
        }
    }

    public void validateBelongsToArticle(Long articleId) {
        if (!this.article.getId().equals(articleId)) {
            throw new CommentException(COMMENT_ARTICLE_MISMATCH);
        }
    }

    private static void validateArticle(Article article) {
        if (article == null) {
            throw new CommentException(COMMENT_ARTICLE_REQUIRED);
        }
    }

    private static void validateMember(Member member) {
        if (member == null) {
            throw new CommentException(COMMENT_MEMBER_REQUIRED);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new CommentException(COMMENT_CONTENT_REQUIRED);
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new CommentException(COMMENT_CONTENT_TOO_LONG);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment comment)) {
            return false;
        }
        return this.id != null && this.id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
