package org.sopt.member.domain;

import org.sopt.util.exception.GeneralException;

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
            throw new GeneralException(GENDER_REQUIRED);
        }

        return new Member(id, name, birthDate, email, gender);
    }

    public Long getId() {
        return this.id;
    }

    public void setNewId(Long id) {
        if (this.id != null) {
            throw new GeneralException(ID_ALREADY_EXISTS);
        }
        this.id = id;
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

        return this.id != null && this.id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GeneralException(NAME_REQUIRED);
        }

        String trimmedName = name.trim();
        String namePattern = "^[가-힣\\s]+$";

        if (!trimmedName.matches(namePattern)) {
            throw new GeneralException(NAME_KOREAN_ONLY);
        }

        if (trimmedName.length() < 2) {
            throw new GeneralException(NAME_MIN_LENGTH);
        }

        if (trimmedName.length() > 50) {
            throw new GeneralException(NAME_MAX_LENGTH);
        }

        if (trimmedName.contains(" ")) {
            throw new GeneralException(NAME_NO_SPACE);
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new GeneralException(EMAIL_REQUIRED);
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new GeneralException(EMAIL_INVALID_FORMAT);
        }
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new GeneralException(BIRTH_DATE_REQUIRED);
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new GeneralException(BIRTH_DATE_FUTURE);
        }
    }

    public static void validateMinimumAge(LocalDate birthDate, int minimumAge) {
        validateBirthDate(birthDate);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < minimumAge) {
            throw new GeneralException(MINIMUM_AGE, minimumAge);
        }
    }
}
