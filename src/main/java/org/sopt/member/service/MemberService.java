package org.sopt.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.entity.Gender;
import org.sopt.member.entity.Member;
import org.sopt.member.repository.MemberRepository;
import org.sopt.util.exception.GeneralException;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.util.exception.ValidationErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberCreateRequest request) {
        validateDuplicateEmail(request.email());

        LocalDate birthDate = parseBirthDate(request.birthDate());
        Gender gender = Gender.fromString(request.gender());
        Member member = Member.create(request.name(), birthDate, request.email(), gender);

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAllBy();
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
    }

    public MemberResponse findOne(Long memberId) {
        return memberRepository.findProjectionById(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new GeneralException(MemberErrorCode.MEMBER_DUPLICATE_EMAIL, email);
                });
    }

    private LocalDate parseBirthDate(String birthDateStr) {
        if (birthDateStr == null || birthDateStr.trim().isEmpty()) {
            throw new GeneralException(ValidationErrorCode.MEMBER_BIRTH_DATE_REQUIRED);
        }

        try {
            return LocalDate.parse(birthDateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new GeneralException(ValidationErrorCode.MEMBER_BIRTH_DATE_REQUIRED);
        }
    }
}
