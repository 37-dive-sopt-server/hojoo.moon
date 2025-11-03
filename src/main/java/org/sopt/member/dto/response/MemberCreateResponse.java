package org.sopt.member.dto.response;

public record MemberCreateResponse(
        Long id
) {
    public static MemberCreateResponse from(Long id) {
        return new MemberCreateResponse(id);
    }
}
