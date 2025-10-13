package org.sopt.domain;

public enum Gender {
    MALE("남성"),
    FEMALE("여성");
    private final String korean;

    Gender(String korean) {
        this.korean = korean;
    }

    public static Gender fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("성별은 필수 입력 항목입니다.");
        }

        String normalized = input.trim().toUpperCase();
        return switch (normalized) {
            case "남성" -> MALE;
            case "여성" -> FEMALE;
            default -> throw new IllegalArgumentException("유효하지 않은 성별 입력입니다: " + input);
        };
    }
}