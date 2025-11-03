package org.sopt.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberCreateRequest(
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        String name,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효하지 않은 이메일 형식입니다.")
        String email,

        @NotBlank(message = "생년월일은 필수 입력 항목입니다.")
        @Pattern(regexp = "^\\d{8}$", message = "생년월일은 YYYYMMDD 형식으로 입력해주세요.")
        String birthDate,

        @NotBlank(message = "성별은 필수 입력 항목입니다.")
        String gender
) {
}