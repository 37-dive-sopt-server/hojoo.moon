package org.sopt.member.controller;

import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.service.MemberService;
import org.sopt.util.exception.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/users")
    public ResponseEntity<BaseResponse<Long>> createMember(@RequestBody MemberCreateRequest request) {
        Long memberId = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.onSuccess(memberId));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<BaseResponse<MemberResponse>> getMembers(@PathVariable Long id) {
        MemberResponse response = MemberResponse.from(memberService.findOne(id));
        return ResponseEntity.ok(BaseResponse.onSuccess(response));
    }

    @GetMapping("/users/all")
    public ResponseEntity<BaseResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers()
                .stream()
                .map(MemberResponse::from)
                .toList();
        return ResponseEntity.ok(BaseResponse.onSuccess(members));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
