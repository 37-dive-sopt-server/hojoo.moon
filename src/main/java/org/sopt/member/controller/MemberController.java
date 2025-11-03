package org.sopt.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.member.controller.spec.MemberControllerDocs;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberCreateResponse;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.entity.Member;
import org.sopt.member.service.MemberService;
import org.sopt.util.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<MemberCreateResponse>> createMember(@RequestBody MemberCreateRequest request) {
        Long memberId = memberService.join(request);
        MemberCreateResponse response = MemberCreateResponse.from(memberId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.onCreated(response));
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberResponse>>> getAllMembers() {
        List<Member> members = memberService.findAllMembers();
        List<MemberResponse> response = members.stream()
                .map(MemberResponse::from)
                .toList();
        return ResponseEntity.ok(BaseResponse.onSuccess(response));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberResponse>> getMemberById(@PathVariable Long id) {
        Member member = memberService.findOne(id);
        MemberResponse response = MemberResponse.from(member);
        return ResponseEntity.ok(BaseResponse.onSuccess(response));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(BaseResponse.onSuccess());
    }
}
