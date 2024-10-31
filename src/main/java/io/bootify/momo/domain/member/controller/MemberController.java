package io.bootify.momo.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bootify.momo.domain.member.dto.request.MemberRequest;
import io.bootify.momo.domain.member.dto.response.MemberResponse;
import io.bootify.momo.domain.member.service.MemberService;
import io.bootify.momo.util.ReferencedException;
import io.bootify.momo.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버 주소
@RestController
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

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
        // Google ID를 기준으로 기존 사용자가 있는지 확인
        MemberResponse existingMember = memberService.findByGoogleId(memberRequest.getGoogleId());

        if (existingMember == null) {
            // 기존 사용자가 없다면 404 상태로 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            // 기존 사용자가 있다면 기존 사용자 정보 반환
            return ResponseEntity.ok(existingMember);
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

            // profileImg가 null인 경우 빈 배열로 설정
            if (profileImg != null && !profileImg.isEmpty()) {
                memberRequest.setProfileImg(profileImg.getBytes());
            }

            // 신규 유저를 등록합니다.
            Long createdId = memberService.create(memberRequest, profileImg); // 수정된 부분
            MemberResponse newMember = memberService.get(createdId); // 새로 생성된 유저 정보 반환
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 발생 시 서버 로그에 출력
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
