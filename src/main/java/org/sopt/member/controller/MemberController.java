package org.sopt.member.controller;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/users")
    public Long createMember(String name, String email, String birthday, String gender) {
        return memberService.join(name, LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd")), email, Gender.fromString(gender));
    }

    @GetMapping("/users/{id}")
    public Member getMembers(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    @GetMapping("/users/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }
}
