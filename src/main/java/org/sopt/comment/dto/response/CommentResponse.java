package org.sopt.comment.dto.response;

import org.sopt.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getName(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
