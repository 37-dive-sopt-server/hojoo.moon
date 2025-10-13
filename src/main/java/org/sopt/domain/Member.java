package org.sopt.domain;

import java.time.LocalDate;

public record Member(Long id, String name, LocalDate birthDate, String email, Gender gender) {

    public Member {
        validateName(name);
        validateEmail(email);
        validateBirthDate(birthDate);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 필수 입력 항목입니다.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 입력 항목입니다.");
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("생년월일은 필수 입력 항목입니다.");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("생년월일은 현재 날짜보다 이전이어야 합니다.");
        }
    }
}
