package org.sopt.member.service;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class MemberService {

    private static final int MINIMUM_AGE = 20;
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(String name, LocalDate birthDate, String email, Gender gender) {
        validateDuplicateEmail(email);
        validateMinimumAge(birthDate);

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

    private void validateMinimumAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < MINIMUM_AGE) {
            throw new IllegalArgumentException("만 " + MINIMUM_AGE + "세 미만은 가입할 수 없습니다.");
        }
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long memberId) {
        findOne(memberId);
        memberRepository.deleteById(memberId);
    }

    public void flush() {
        memberRepository.saveToFile();
    }
}