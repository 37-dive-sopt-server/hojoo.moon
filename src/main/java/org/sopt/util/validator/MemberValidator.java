package org.sopt.util.validator;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;

import java.time.LocalDate;

public class MemberValidator {

    public static void validateName(String name) {
        Member.validateName(name);
    }

    public static void validateEmail(String email) {
        Member.validateEmail(email);
    }

    public static void validateBirthDate(LocalDate birthDate) {
        Member.validateBirthDate(birthDate);
    }

    public static Gender validateGender(String genderInput) {
        return Gender.fromString(genderInput);
    }

    public static void validateMinimumAge(LocalDate birthDate, int minimumAge) {
        Member.validateMinimumAge(birthDate, minimumAge);
    }
}