package org.sopt.article.repository;

import org.sopt.article.dto.response.ArticleDetailResponse;
import org.sopt.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT new org.sopt.article.dto.response.ArticleDetailResponse(" +
            "a.id, a.title, a.content, a.tag, a.createdAt, a.updatedAt, a.member.name)" +
            "FROM Article a " +
            "WHERE a.id = :id")
    Optional<ArticleDetailResponse> findProjectionById(@Param("id") Long id);

    boolean existsByTitle(String title);
}
