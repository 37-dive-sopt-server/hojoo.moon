package org.sopt.member.dto.response;

import org.sopt.member.entity.Gender;

import java.time.LocalDate;


public interface MemberResponse {
    Long getId();
    String getName();
    String getEmail();
    LocalDate getBirthDate();
    Gender getGender();
}