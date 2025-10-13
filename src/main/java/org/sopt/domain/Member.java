package org.sopt.domain;

import java.time.LocalDate;

public record Member(Long id, String name, LocalDate birthDate, String email, Gender gender) {

    public Member {
        MemberValidator.validateName(name);
        MemberValidator.validateEmail(email);
        MemberValidator.validateBirthDate(birthDate);
    }
}
