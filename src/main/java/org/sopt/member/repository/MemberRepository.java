package org.sopt.member.repository;

import org.sopt.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    default void saveToFile() {

    }

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Member> findAll();

    void deleteById(Long id);
}