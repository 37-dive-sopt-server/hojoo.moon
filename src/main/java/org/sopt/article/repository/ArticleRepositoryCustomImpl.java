package org.sopt.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.article.dto.request.ArticleSearchCondition;
import org.sopt.article.dto.response.ArticleListResponse;
import org.springframework.util.StringUtils;

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
                        titleFullText(condition.title()),
                        authorNameContains(condition.authorName())
                )
                .orderBy(article.createdAt.desc())
                .fetch();
    }

    private BooleanExpression titleFullText(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        NumberTemplate<Double> relevance = Expressions.numberTemplate(
                Double.class,
                "function('match_against', {0}, {1}, {2})",
                article.title,
                article.content,
                title
        );
        return relevance.gt(0d);
    }

    private BooleanExpression authorNameContains(String authorName) {
        return StringUtils.hasText(authorName) ? member.name.contains(authorName) : null;
    }
}
