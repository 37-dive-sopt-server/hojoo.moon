package org.sopt.comment.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.util.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관리 API")
public interface CommentControllerDocs {

    @Operation(summary = "댓글 생성", description = "게시글에 새로운 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글 또는 작성자를 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<Long>> createComment(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long articleId,
            @Valid @RequestBody CommentCreateRequest request
    );

    @Operation(summary = "댓글 목록 조회", description = "게시글의 모든 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentsByArticle(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long articleId
    );

    @Operation(summary = "댓글 상세 조회", description = "특정 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 댓글을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<CommentResponse>> getCommentById(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long articleId,
            @Parameter(description = "댓글 ID", required = true) @PathVariable Long commentId
    );

    @Operation(summary = "댓글 수정", description = "댓글 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글 또는 댓글을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<Void>> updateComment(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long articleId,
            @Parameter(description = "댓글 ID", required = true) @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    );

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글 또는 댓글을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<Void>> deleteComment(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long articleId,
            @Parameter(description = "댓글 ID", required = true) @PathVariable Long commentId,
            @Parameter(description = "작성자 ID", required = true) @RequestParam Long memberId
    );
}
