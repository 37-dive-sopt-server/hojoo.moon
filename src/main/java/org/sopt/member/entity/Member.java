package org.sopt.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.article.entity.Article;
import org.sopt.util.exception.GeneralException;

import java.time.LocalDate;
import java.time.Period;
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

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

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
        if (gender == null) {
            throw new GeneralException(MEMBER_GENDER_REQUIRED);
        }

        return new Member(name, birthDate, email, gender);
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
            throw new GeneralException(MEMBER_NAME_REQUIRED);
        }

        String trimmedName = name.trim();
        String namePattern = "^[가-힣\\s]+$";

        if (!trimmedName.matches(namePattern)) {
            throw new GeneralException(MEMBER_NAME_KOREAN_ONLY);
        }

        if (trimmedName.length() < 2) {
            throw new GeneralException(MEMBER_NAME_MIN_LENGTH);
        }

        if (trimmedName.length() > 50) {
            throw new GeneralException(MEMBER_NAME_MAX_LENGTH);
        }

        if (trimmedName.contains(" ")) {
            throw new GeneralException(MEMBER_NAME_NO_SPACE);
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new GeneralException(MEMBER_EMAIL_REQUIRED);
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new GeneralException(MEMBER_EMAIL_INVALID_FORMAT);
        }
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new GeneralException(MEMBER_BIRTH_DATE_REQUIRED);
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new GeneralException(MEMBER_BIRTH_DATE_FUTURE);
        }
    }

    public static void validateMinimumAge(LocalDate birthDate, int minimumAge) {
        validateBirthDate(birthDate);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < minimumAge) {
            throw new GeneralException(MEMBER_MINIMUM_AGE, minimumAge);
        }
    }
}
