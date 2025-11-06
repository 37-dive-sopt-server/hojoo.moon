package org.sopt.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.sopt.article.exception.ArticleException;
import org.sopt.member.entity.Member;
import org.sopt.util.entity.BaseEntity;

import java.util.Objects;

import static org.sopt.article.exception.ArticleErrorCode.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Article extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @BatchSize(size = 10)
    private Member member;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleTag tag;

    public static Article create(Member member, String title, String content, ArticleTag tag) {
        validateMember(member);
        validateTitle(title);
        validateContent(content);
        validateTag(tag);

        return Article.builder()
                .member(member)
                .title(title)
                .content(content)
                .tag(tag)
                .build();
    }

    private static void validateMember(Member member) {
        if (member == null) {
            throw new ArticleException(ARTICLE_MEMBER_REQUIRED);
        }
    }

    private static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ArticleException(ARTICLE_TITLE_REQUIRED);
        }
        if (title.length() > 100) {
            throw new ArticleException(ARTICLE_TITLE_TOO_LONG);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new ArticleException(ARTICLE_CONTENT_REQUIRED);
        }
        if (content.length() > 5000) {
            throw new ArticleException(ARTICLE_CONTENT_TOO_LONG);
        }
    }

    private static void validateTag(ArticleTag tag) {
        if (tag == null) {
            throw new ArticleException(ARTICLE_TAG_REQUIRED);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article article)) {
            return false;
        }
        return this.id != null && this.id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
