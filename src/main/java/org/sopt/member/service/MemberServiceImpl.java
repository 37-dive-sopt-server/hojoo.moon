package org.sopt.member.service;

import org.sopt.util.exception.GeneralException;
import org.sopt.member.domain.Gender;
import org.sopt.member.repository.MemberRepository;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.sopt.util.exception.ErrorCode.*;

@Service
public class MemberServiceImpl implements MemberService {

    private static final int MINIMUM_AGE = 20;
    @Autowired
    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long join(MemberCreateRequest request) {
        String email = request.email();
        validateDuplicateEmail(email);

        LocalDate birthDate = LocalDate.parse(request.birthDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        Member.validateMinimumAge(birthDate, MINIMUM_AGE);

        Gender gender = Gender.fromString(request.gender());

        Member member = Member.create(null, request.name(), birthDate, email, gender);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new GeneralException(DUPLICATE_EMAIL, email);
        }
    }

    @Override
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND, memberId));
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
