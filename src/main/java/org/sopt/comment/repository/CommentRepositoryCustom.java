package org.sopt.comment.repository;

import org.sopt.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentResponse> findAllByArticleId(Long articleId);
}
