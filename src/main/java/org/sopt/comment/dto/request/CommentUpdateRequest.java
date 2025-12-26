package org.sopt.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentUpdateRequest(
        @NotNull(message = "작성자 ID는 필수 입력 항목입니다.")
        Long memberId,

        @NotBlank(message = "댓글 내용은 필수 입력 항목입니다.")
        @Size(min = 1, max = 1000, message = "댓글은 최소 1자 이상, 최대 1000자까지 입력 가능합니다.")
        String content
) {
}
