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

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    private Member(String name, LocalDate birthDate, String email, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
    }

    public static Member create(String name, LocalDate birthDate, String email, Gender gender) {
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new GeneralException(MEMBER_BIRTH_DATE_FUTURE);
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
}
