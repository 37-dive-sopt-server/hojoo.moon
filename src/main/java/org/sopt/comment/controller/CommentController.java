package org.sopt.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.controller.spec.CommentControllerDocs;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<Long>> createComment(
            @PathVariable Long articleId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.onCreated(commentService.createComment(articleId, request)));
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentsByArticle(
            @PathVariable Long articleId
    ) {
        return ResponseEntity.ok(BaseResponse.onSuccess(commentService.findCommentsByArticleId(articleId)));
    }

    @Override
    @GetMapping("/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> getCommentById(
            @PathVariable Long articleId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(BaseResponse.onSuccess(commentService.findCommentById(articleId, commentId)));
    }

    @Override
    @PutMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> updateComment(
            @PathVariable Long articleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        commentService.updateComment(articleId, commentId, request);
        return ResponseEntity.ok(BaseResponse.onSuccess());
    }

    @Override
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(
            @PathVariable Long articleId,
            @PathVariable Long commentId,
            @RequestParam Long memberId
    ) {
        commentService.deleteComment(articleId, commentId, memberId);
        return ResponseEntity.ok(BaseResponse.onSuccess());
    }
}
