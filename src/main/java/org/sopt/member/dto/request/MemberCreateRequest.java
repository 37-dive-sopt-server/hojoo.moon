package org.sopt.member.dto.request;

public record MemberCreateRequest(
        String name,
        String email,
        String birthDate,
        String gender
) {
}