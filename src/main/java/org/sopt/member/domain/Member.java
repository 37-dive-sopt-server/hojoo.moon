package org.sopt.member.domain;

import org.sopt.util.exception.ValidationException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static org.sopt.util.exception.ErrorCode.*;

public class Member {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private Gender gender;

    private Member(Long id, String name, LocalDate birthDate, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
    }

    public static Member create(Long id, String name, LocalDate birthDate, String email, Gender gender) {
        validateName(name);
        validateEmail(email);
        validateBirthDate(birthDate);
        if (gender == null) {
            throw new ValidationException(GENDER_REQUIRED);
        }

        return new Member(id, name, birthDate, email, gender);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Member member)) {
            return false;
        }

        return id != null && id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException(NAME_REQUIRED);
        }

        String trimmedName = name.trim();
        String namePattern = "^[가-힣\\s]+$";

        if (!trimmedName.matches(namePattern)) {
            throw new ValidationException(NAME_KOREAN_ONLY);
        }

        if (trimmedName.length() < 2) {
            throw new ValidationException(NAME_MIN_LENGTH);
        }

        if (trimmedName.length() > 50) {
            throw new ValidationException(NAME_MAX_LENGTH);
        }

        if (trimmedName.contains(" ")) {
            throw new ValidationException(NAME_NO_SPACE);
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException(EMAIL_REQUIRED);
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new ValidationException(EMAIL_INVALID_FORMAT);
        }
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new ValidationException(BIRTH_DATE_REQUIRED);
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new ValidationException(BIRTH_DATE_FUTURE);
        }
    }

    public static void validateMinimumAge(LocalDate birthDate, int minimumAge) {
        validateBirthDate(birthDate);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < minimumAge) {
            throw new ValidationException(MINIMUM_AGE, minimumAge);
        }
    }
}
