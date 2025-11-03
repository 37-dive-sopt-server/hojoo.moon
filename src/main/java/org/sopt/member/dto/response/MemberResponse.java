package org.sopt.member.dto.response;

import org.sopt.member.entity.Member;

import java.time.format.DateTimeFormatter;

public record MemberResponse(
        Long id,
        String name,
        String email,
        String birthDate,
        String gender
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getBirthDate().format(DATE_FORMATTER),
                member.getGender().name()
        );
    }
}