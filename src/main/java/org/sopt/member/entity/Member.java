package org.sopt.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.article.entity.Article;
import org.sopt.util.exception.GeneralException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.sopt.util.exception.ValidationErrorCode.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Member(String name, LocalDate birthDate, String email, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
    }

    public static Member create(String name, LocalDate birthDate, String email, Gender gender) {
        validateName(name);
        validateEmail(email);
        validateBirthDate(birthDate);
        validateGender(gender);

        return new Member(name, birthDate, email, gender);
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GeneralException(MEMBER_NAME_REQUIRED);
        }
        if (!name.matches("^[가-힣]+$")) {
            throw new GeneralException(MEMBER_NAME_INVALID_FORMAT);
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new GeneralException(MEMBER_NAME_INVALID_LENGTH);
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new GeneralException(MEMBER_EMAIL_REQUIRED);
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new GeneralException(MEMBER_EMAIL_INVALID_FORMAT);
        }
    }

    private static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new GeneralException(MEMBER_BIRTH_DATE_REQUIRED);
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new GeneralException(MEMBER_BIRTH_DATE_FUTURE);
        }
    }

    private static void validateGender(Gender gender) {
        if (gender == null) {
            throw new GeneralException(MEMBER_GENDER_REQUIRED);
        }
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
}
