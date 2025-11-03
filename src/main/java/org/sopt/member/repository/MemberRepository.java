package org.sopt.member.repository;

import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    List<MemberResponse> findAllBy();

    Optional<MemberResponse> findProjectionById(Long id);

}
