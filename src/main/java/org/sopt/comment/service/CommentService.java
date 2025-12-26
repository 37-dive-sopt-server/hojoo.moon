package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.entity.Article;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.article.exception.ArticleException;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.entity.Comment;
import org.sopt.comment.exception.CommentErrorCode;
import org.sopt.comment.exception.CommentException;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.member.entity.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createComment(Long articleId, CommentCreateRequest request) {
        Article article = findArticleById(articleId);
        Member member = findMemberById(request.memberId());

        Comment comment = Comment.create(article, member, request.content());
        return commentRepository.save(comment).getId();
    }

    public List<CommentResponse> findCommentsByArticleId(Long articleId) {
        validateArticleExists(articleId);
        return commentRepository.findAllByArticleId(articleId);
    }

    public CommentResponse findCommentById(Long articleId, Long commentId) {
        Comment comment = findCommentAndValidateArticle(articleId, commentId);
        return CommentResponse.from(comment);
    }

    @Transactional
    public void updateComment(Long articleId, Long commentId, CommentUpdateRequest request) {
        Comment comment = findCommentAndValidateArticle(articleId, commentId);
        comment.validateOwnership(request.memberId());
        comment.updateContent(request.content());
    }

    @Transactional
    public void deleteComment(Long articleId, Long commentId, Long memberId) {
        Comment comment = findCommentAndValidateArticle(articleId, commentId);
        comment.validateOwnership(memberId);
        commentRepository.delete(comment);
    }

    private Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_ARTICLE_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_MEMBER_NOT_FOUND));
    }

    private void validateArticleExists(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND);
        }
    }

    private Comment findCommentAndValidateArticle(Long articleId, Long commentId) {
        validateArticleExists(articleId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.validateBelongsToArticle(articleId);
        return comment;
    }
}
