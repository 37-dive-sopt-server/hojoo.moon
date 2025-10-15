package org.sopt.member.service;

import org.sopt.member.domain.Gender;
import org.sopt.member.repository.MemberRepository;
import org.sopt.member.domain.Member;

import java.time.LocalDate;
import java.util.List;

public class MemberServiceImpl implements MemberService {

    private static final int MINIMUM_AGE = 20;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long join(String name, LocalDate birthDate, String email, Gender gender) {
        validateDuplicateEmail(email);
        Member.validateMinimumAge(birthDate, MINIMUM_AGE);

        Long id = memberRepository.generateNextId();
        Member member = Member.create(id, name, birthDate, email, gender);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다: " + email);
                });
    }

    @Override
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(Long memberId) {
        findOne(memberId);
        memberRepository.deleteById(memberId);
    }

    @Override
    public void flush() {
        memberRepository.saveToFile();
    }
}
