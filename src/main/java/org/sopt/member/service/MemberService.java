package org.sopt.member.service;

import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.MemberCreateRequest;

import java.util.List;

public interface MemberService {

    Long join(MemberCreateRequest request);

    Member findOne(Long memberId);

    List<Member> findAllMembers();

    void deleteMember(Long memberId);

    void flush();
}