package org.sopt.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.member.controller.spec.MemberControllerDocs;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberResponse;
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
    public ResponseEntity<BaseResponse<Long>> createMember(
            @Valid @RequestBody MemberCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.onCreated(memberService.join(request)));
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> response = memberService.findAllMembers();
        return ResponseEntity.ok(BaseResponse.onSuccess(response));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberResponse>> getMemberById(@PathVariable Long id) {
        MemberResponse response = memberService.findOne(id);
        return ResponseEntity.ok(BaseResponse.onSuccess(response));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(BaseResponse.onSuccess());
    }
}
