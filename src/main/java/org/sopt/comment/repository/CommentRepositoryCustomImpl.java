package org.sopt.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.response.CommentResponse;

import java.util.List;

import static org.sopt.comment.entity.QComment.comment;
import static org.sopt.member.entity.QMember.member;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponse> findAllByArticleId(Long articleId) {
        return queryFactory
                .select(Projections.constructor(CommentResponse.class,
                        comment.id,
                        comment.content,
                        member.name,
                        comment.createdAt,
                        comment.updatedAt
                ))
                .from(comment)
                .join(comment.member, member)
                .where(comment.article.id.eq(articleId))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }
}
