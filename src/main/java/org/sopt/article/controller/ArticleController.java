package org.sopt.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.article.controller.spec.ArticleControllerDocs;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.response.ArticleDetailResponse;
import org.sopt.article.service.ArticleService;
import org.sopt.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController implements ArticleControllerDocs {

    private final ArticleService articleService;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<Long>> createArticle(
            @Valid @RequestBody ArticleCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.onCreated(articleService.createArticle(request)));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ArticleDetailResponse>> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponse.onSuccess(articleService.findArticleById(id)));
    }

}
