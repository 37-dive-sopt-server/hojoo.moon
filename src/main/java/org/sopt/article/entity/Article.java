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

}
