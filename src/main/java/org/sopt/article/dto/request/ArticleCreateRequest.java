package org.sopt.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArticleCreateRequest(
        @NotNull(message = "작성자 ID는 필수 입력 항목입니다.")
        Long memberId,

        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        @Size(min = 1, max = 100, message = "제목은 최소 1자 이상, 최대 100자까지 입력 가능합니다.")
        String title,

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        @Size(min = 1, max = 5000, message = "내용은 최소 1자 이상, 최대 5000자까지 입력 가능합니다.")
        String content,

        @NotBlank(message = "태그는 필수 입력 항목입니다.")
        String tag
) {
}