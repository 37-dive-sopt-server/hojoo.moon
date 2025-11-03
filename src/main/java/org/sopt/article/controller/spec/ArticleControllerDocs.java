package org.sopt.article.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.response.ArticleDetailResponse;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.util.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Article", description = "게시글 관리 API")
public interface ArticleControllerDocs {

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "404", description = "작성자를 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 제목", content = @Content)
    })
    ResponseEntity<BaseResponse<Long>> createArticle(
            @Valid @RequestBody ArticleCreateRequest request
    );

    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<ArticleDetailResponse>> getArticleById(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long id
    );

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글의 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<BaseResponse<List<ArticleListResponse>>> getAllArticles();

    @Operation(summary = "게시글 검색", description = "제목과 작성자 이름으로 게시글을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공")
    })
    ResponseEntity<BaseResponse<List<ArticleListResponse>>> searchArticles(
            @Parameter(description = "게시글 제목") @RequestParam(required = false) String title,
            @Parameter(description = "작성자 이름") @RequestParam(required = false) String authorName
    );
}