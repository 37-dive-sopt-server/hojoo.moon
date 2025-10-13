package org.sopt.service;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(String name, LocalDate birthDate, String email, Gender gender) {
        validateDuplicateEmail(email);

        Long id = memberRepository.generateNextId();
        Member member = new Member(id, name, birthDate, email, gender);
        memberRepository.save(member);
        return member.id();
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다: " + email);
                });
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }
}