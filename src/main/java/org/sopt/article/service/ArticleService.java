package org.sopt.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.request.ArticleSearchCondition;
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

    // projection으로 해결
    public ArticleDetailResponse findArticleById(Long articleId) {
        return articleRepository.findDtoById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));
    }

    // projection으로 해결
    public List<ArticleListResponse> findAllArticles() {
        return articleRepository.findAllDto();
    }

    public List<ArticleListResponse> searchArticles(ArticleSearchCondition condition) {
        return articleRepository.search(condition);
    }
}
