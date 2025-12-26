package org.sopt.comment.repository;

import org.sopt.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    List<Comment> findByArticleIdOrderByCreatedAtDesc(Long articleId);

    boolean existsByIdAndArticleId(Long id, Long articleId);

    void deleteAllByArticleId(Long articleId);
}
