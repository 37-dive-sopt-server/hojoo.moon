package org.sopt.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

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
            throw new IllegalArgumentException("이름은 필수 입력 항목입니다.");
        }

        String trimmedName = name.trim();
        String namePattern = "^[가-힣\\s]+$";

        if (!trimmedName.matches(namePattern)) {
            throw new IllegalArgumentException("이름은 한글만 입력 가능합니다.");
        }

        if (trimmedName.length() < 2) {
            throw new IllegalArgumentException("이름은 최소 2자 이상이어야 합니다.");
        }

        if (trimmedName.length() > 50) {
            throw new IllegalArgumentException("이름은 최대 50자까지 입력 가능합니다.");
        }

        if (trimmedName.contains(" ")) {
            throw new IllegalArgumentException("이름에 공백은 사용할 수 없습니다.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 입력 항목입니다.");
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("생년월일은 필수 입력 항목입니다.");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("생년월일은 현재 날짜보다 이전이어야 합니다.");
        }
    }
}
