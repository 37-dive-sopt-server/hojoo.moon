package org.sopt.member.entity;

import org.sopt.util.exception.GeneralException;

import static org.sopt.util.exception.ValidationErrorCode.*;

public enum Gender {
    MALE("남성"),
    FEMALE("여성");
    private final String korean;

    Gender(String korean) {
        this.korean = korean;
    }

    public static Gender fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new GeneralException(MEMBER_GENDER_REQUIRED);
        }

        String normalized = input.trim().toUpperCase();
        return switch (normalized) {
            case "남성" -> MALE;
            case "여성" -> FEMALE;
            default -> throw new GeneralException(MEMBER_GENDER_INVALID, input);
        };
    }
}