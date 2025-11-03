package org.sopt.article.repository;

import org.sopt.article.dto.response.ArticleDetailResponse;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.article.entity.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT new org.sopt.article.dto.response.ArticleDetailResponse(" +
            "a.id, a.title, a.content, a.tag, a.createdAt, a.member.name)" +
            "FROM Article a " +
            "WHERE a.id = :id")
    Optional<ArticleDetailResponse> findDtoById(@Param("id") Long id);

    @Query("SELECT new org.sopt.article.dto.response.ArticleListResponse(" +
            "a.title, a.createdAt, a.member.name) " +
            "FROM Article a " +
            "ORDER BY a.createdAt DESC")
    List<ArticleListResponse> findAllDto();

    @Query("SELECT a FROM Article a JOIN FETCH a.member ORDER BY a.createdAt DESC")
    List<Article> findAllWithFetchJoin();

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT a FROM Article a ORDER BY a.createdAt DESC")
    List<Article> findAllWithEntityGraph();

    boolean existsByTitle(String title);
}
