package org.sopt.util;

import org.sopt.domain.Member;

import java.time.LocalDate;

public class InputValidator {

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