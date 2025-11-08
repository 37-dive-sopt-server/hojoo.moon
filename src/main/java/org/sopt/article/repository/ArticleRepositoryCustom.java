package org.sopt.article.repository;

import org.sopt.article.dto.request.ArticleSearchCondition;
import org.sopt.article.dto.response.ArticleListResponse;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<ArticleListResponse> search(ArticleSearchCondition condition);
}