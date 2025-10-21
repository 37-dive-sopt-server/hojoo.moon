package org.sopt.member.controller;

import org.sopt.member.domain.Gender;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberResponse;
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
    public Long createMember(@RequestBody MemberCreateRequest request) {
        return memberService.join(
                request.name(),
                LocalDate.parse(request.birthDate(), DateTimeFormatter.ofPattern("yyyyMMdd")),
                request.email(),
                Gender.fromString(request.gender())
        );
    }

    @GetMapping("/users/{id}")
    public MemberResponse getMembers(@PathVariable Long id) {
        return MemberResponse.from(memberService.findOne(id));
    }

    @GetMapping("/users/all")
    public List<MemberResponse> getAllMembers() {
        return memberService.findAllMembers()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    @DeleteMapping("/users/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
