package com.ureca.momo.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.momo.domain.member.dto.request.MemberRequest;
import com.ureca.momo.domain.member.dto.response.MemberResponse;
import com.ureca.momo.domain.member.service.MemberService;
import com.ureca.momo.util.ReferencedException;
import com.ureca.momo.util.ReferencedWarning;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버 주소
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(memberService.get(id));
    }

    @PostMapping("/google-login")
    public ResponseEntity<MemberResponse> googleLogin(@RequestBody @Valid final MemberRequest memberRequest) {
        try {
            // Google ID를 기준으로 기존 사용자가 있는지 확인
            String googleId = memberRequest.googleId();
            if (googleId == null || googleId.isEmpty()) {
                return ResponseEntity.badRequest().body(null); // Google ID가 없으면 잘못된 요청으로 처리
            }

            MemberResponse existingMember = memberService.findByGoogleId(googleId);

            if (existingMember == null) {
                // 기존 사용자가 없다면 404 상태로 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                // 기존 사용자가 있다면 기존 사용자 정보 반환
                return ResponseEntity.ok(existingMember);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 예외 메시지 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(
            @RequestPart("userData") @Valid final String userDataJson,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        try {
            // userDataJson을 MemberRequest 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            MemberRequest memberRequest = objectMapper.readValue(userDataJson, MemberRequest.class);

            // 프로필 이미지가 있는 경우, 새로운 MemberRequest 객체를 생성하여 필드를 업데이트
            if (profileImg != null && !profileImg.isEmpty()) {
                memberRequest = new MemberRequest(
                        memberRequest.username(),
                        memberRequest.contact(),
                        memberRequest.googleId(),
                        profileImg.getBytes()
                );
            }

            // 신규 유저를 등록합니다.
            Long createdId = memberService.create(memberRequest, profileImg);
            MemberResponse newMember = memberService.get(createdId); // 새로 생성된 유저 정보 반환
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        } catch (IOException e) {
            // 프로필 이미지 처리 중 발생한 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMember(@PathVariable(name = "id") final Long id,
                                             @RequestPart("userData") @Valid final MemberRequest memberRequest,
                                             @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        try {
            // 프로필 이미지 파일이 있는 경우에만 전달
            memberService.update(id, memberRequest, profileImg);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 계정 삭제 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(name = "id") final Long id) {
        // 회원 참조 경고 확인 (필요 시)
        final ReferencedWarning referencedWarning = memberService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }

        // 삭제 서비스 호출
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
