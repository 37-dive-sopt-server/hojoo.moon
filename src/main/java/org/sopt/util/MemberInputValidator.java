package org.sopt.util;

import org.sopt.member.domain.Member;

import java.time.LocalDate;

public class MemberInputValidator {

    public static void validateName(String name) {
        Member.validateName(name);
    }

    public static void validateEmail(String email) {
        Member.validateEmail(email);
    }

    public static void validateBirthDate(LocalDate birthDate) {
        Member.validateBirthDate(birthDate);
    }
}