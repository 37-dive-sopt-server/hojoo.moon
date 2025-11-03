package org.sopt.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.member.entity.Member;
import org.sopt.util.entity.BaseEntity;

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
    private Member member;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleTag tag;

    public static Article create(Member member, String title, String content, ArticleTag tag) {
        return Article.builder()
                .member(member)
                .title(title)
                .content(content)
                .tag(tag)
                .build();
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void updateTag(ArticleTag newTag) {
        this.tag = newTag;
    }

    public boolean isWrittenBy(Member member) {
        if (member == null || this.member == null) {
            return false;
        }
        return this.member.equals(member);
    }
}
