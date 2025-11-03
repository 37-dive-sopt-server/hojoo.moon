package org.sopt.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
        @Size(min = 2, max = 50, message = "이름은 최소 2자 이상, 최대 50자까지 입력 가능합니다.")
        String name,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효하지 않은 이메일 형식입니다.")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "유효하지 않은 이메일 형식입니다.")
        String email,

        @NotBlank(message = "생년월일은 필수 입력 항목입니다.")
        @Pattern(regexp = "^\\d{8}$", message = "생년월일은 YYYYMMDD 형식으로 입력해주세요.")
        String birthDate,

        @NotBlank(message = "성별은 필수 입력 항목입니다.")
        @Pattern(regexp = "^(MALE|FEMALE)$", message = "성별은 MALE 또는 FEMALE만 입력 가능합니다.")
        String gender
) {
}