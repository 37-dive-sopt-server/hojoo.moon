package org.sopt.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.response.ArticleDetailResponse;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.article.entity.Article;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.article.exception.ArticleException;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.member.entity.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createArticle(ArticleCreateRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_MEMBER_NOT_FOUND));

        if (articleRepository.existsByTitle(request.title())) {
            throw new ArticleException(ArticleErrorCode.ARTICLE_DUPLICATE_TITLE);
        }

        Article article = Article.create(member, request.title(), request.content(), request.tag());
        Article savedArticle = articleRepository.save(article);

        return savedArticle.getId();
    }

    // N+1 발생 메서드 (단일 게시글 조회)
    public ArticleDetailResponse findArticleEntityById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        return ArticleDetailResponse.from(article);
    }

    // projection으로 해결
    public ArticleDetailResponse findArticleById(Long articleId) {
        return articleRepository.findDtoById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));
    }

    // N+1 발생 메서드 (전체 게시글 조회)
    public List<ArticleListResponse> findAllArticleEntities() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleListResponse::from)
                .collect(Collectors.toList());
    }

    // projection으로 해결
    public List<ArticleListResponse> findAllArticles() {
        return articleRepository.findAllDto();
    }

    // fetch join으로 해결
    public List<ArticleListResponse> findAllArticlesWithFetchJoin() {
        return articleRepository.findAllWithFetchJoin()
                .stream()
                .map(ArticleListResponse::from)
                .collect(Collectors.toList());
    }

    // Entity Graph로 해결
    public List<ArticleListResponse> findAllArticlesWithEntityGraph() {
        return articleRepository.findAllWithEntityGraph()
                .stream()
                .map(ArticleListResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * BatchSize로 N+1 문제 완화
     * Article.member 필드에 @BatchSize(size = 10) 적용
     * 쿼리 횟수: 1 + ceil(N/10)번
     * 예) 100개 Article → 1(Article 조회) + 10(Member IN 쿼리) = 11번
     */
    public List<ArticleListResponse> findAllArticlesWithBatchSize() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleListResponse::from)
                .collect(Collectors.toList());
    }
}
