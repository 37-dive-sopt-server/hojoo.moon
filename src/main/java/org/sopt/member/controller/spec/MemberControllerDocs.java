package org.sopt.member.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberCreateResponse;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.util.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Member", description = "회원 관리 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원 가입", description = "새로운 회원을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일", content = @Content)
    })
    ResponseEntity<BaseResponse<MemberCreateResponse>> createMember(
            @Valid @RequestBody MemberCreateRequest request
    );

    @Operation(summary = "전체 회원 조회", description = "모든 회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<BaseResponse<List<MemberResponse>>> getAllMembers();

    @Operation(summary = "회원 상세 조회", description = "특정 회원의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<MemberResponse>> getMemberById(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long id
    );

    @Operation(summary = "회원 삭제", description = "특정 회원을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<BaseResponse<Void>> deleteMember(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long id
    );
}
