package org.sopt.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.article.dto.request.ArticleSearchCondition;
import org.sopt.article.dto.response.ArticleListResponse;

import java.util.List;

import static org.sopt.article.entity.QArticle.article;
import static org.sopt.member.entity.QMember.member;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArticleListResponse> search(ArticleSearchCondition condition) {
        return queryFactory
                .select(Projections.constructor(ArticleListResponse.class,
                        article.title,
                        article.createdAt,
                        member.name
                ))
                .from(article)
                .join(article.member, member)
                .where(
                        titleContains(condition.title()),
                        authorNameContains(condition.authorName())
                )
                .orderBy(article.createdAt.desc())
                .fetch();
    }

    private BooleanExpression titleContains(String title) {
        return title != null && !title.isEmpty() ? article.title.contains(title) : null;
    }

    private BooleanExpression authorNameContains(String authorName) {
        return authorName != null && !authorName.isEmpty() ? member.name.contains(authorName) : null;
    }
}