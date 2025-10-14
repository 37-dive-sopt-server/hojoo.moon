package org.sopt.member.controller;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.member.service.MemberService;

import java.time.LocalDate;
import java.util.List;

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public Long createMember(String name, LocalDate birthDate, String email, Gender gender) {
        return memberService.join(name, birthDate, email, gender);
    }

    public Member findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public void deleteMember(Long id) {
        memberService.deleteMember(id);
    }

    public void shutdown() {
        memberService.flush();
    }
}
