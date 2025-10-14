package org.sopt.member.service;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    Long join(String name, LocalDate birthDate, String email, Gender gender);

    Member findOne(Long memberId);

    List<Member> findAllMembers();

    void deleteMember(Long memberId);

    void flush();
}